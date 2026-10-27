
package com.example;

import com.example.model.Gato;
import com.example.model.Foto;
import com.example.model.Ubicacion;
import com.example.model.Avistamiento;
import com.example.model.User;
import com.example.service.CrudService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("GatoGo")
@Route("")
public class MainView extends VerticalLayout {

    private final CrudService crudService;

    public MainView(CrudService crudService) {

        this.crudService = crudService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        TabSheet tabs = new TabSheet();
        tabs.setWidthFull();

        tabs.add("Gatos", crearSeccionGatos());
        tabs.add("Fotos", crearSeccionFotos());
        tabs.add("Ubicaciones", crearSeccionUbicaciones());
        tabs.add("Avistamientos", crearSeccionAvistamientos());
        tabs.add("Usuarios", crearSeccionUsuarios());

        add(new H2("GatoGo - Gestión CRUD"), tabs);
    }

    // ==========================================
    // 1. GATOS
    // ==========================================

    private Component crearSeccionGatos() {

        VerticalLayout layout = new VerticalLayout();

        TextField idField = new TextField("ID Gato");
        TextField nombreField = new TextField("Nombre");
        TextField colorField = new TextField("Color");
        DatePicker fechaField = new DatePicker("Fecha de ingreso");
        TextArea descripcionField = new TextArea("Descripción");

        idField.setReadOnly(true);

        FormLayout form = new FormLayout(
                idField,
                nombreField,
                colorField,
                fechaField,
                descripcionField
        );

        Grid<Gato> grid = new Grid<>(Gato.class, false);

        grid.addColumn(Gato::getIdGato).setHeader("ID");
        grid.addColumn(Gato::getNombre).setHeader("Nombre");
        grid.addColumn(Gato::getColor).setHeader("Color");
        grid.addColumn(Gato::getFechaIngreso).setHeader("Fecha");
        grid.addColumn(Gato::getDescripcion).setHeader("Descripción");

        Runnable refrescar = () ->
                grid.setItems(crudService.listarTodos(Gato.class));

        Runnable limpiarFormulario = () -> {
            grid.asSingleSelect().clear();
            limpiar(idField, nombreField, colorField,
                    fechaField, descripcionField);
        };

        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {

            Gato gato = e.getValue();

            if (gato != null) {
                ponerId(idField, gato.getIdGato());
                nombreField.setValue(texto(gato.getNombre()));
                colorField.setValue(texto(gato.getColor()));
                fechaField.setValue(gato.getFechaIngreso());
                descripcionField.setValue(texto(gato.getDescripcion()));
            }
        });

        Button btnCrear = new Button("Crear", e -> {

            Gato gato = new Gato();

            gato.setNombre(nombreField.getValue());
            gato.setColor(colorField.getValue());
            gato.setFechaIngreso(fechaField.getValue());
            gato.setDescripcion(descripcionField.getValue());

            crudService.guardar(gato);
            refrescar.run();

            ponerId(idField, gato.getIdGato());

            Notification.show("Gato guardado");
        });

        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {

            Long id = parseId(idField);

            Gato gato = id != null
                    ? crudService.buscarPorId(Gato.class, id)
                    : null;

            if (gato != null) {
                ponerId(idField, gato.getIdGato());
                nombreField.setValue(texto(gato.getNombre()));
                colorField.setValue(texto(gato.getColor()));
                fechaField.setValue(gato.getFechaIngreso());
                descripcionField.setValue(texto(gato.getDescripcion()));

                Notification.show("Gato encontrado");
            } else {
                Notification.show("Gato no encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {

            Long id = parseId(idField);

            Gato gato = id != null
                    ? crudService.buscarPorId(Gato.class, id)
                    : null;

            if (gato != null) {

                gato.setNombre(nombreField.getValue());
                gato.setColor(colorField.getValue());
                gato.setFechaIngreso(fechaField.getValue());
                gato.setDescripcion(descripcionField.getValue());

                crudService.actualizar(gato);
                refrescar.run();

                Notification.show("Gato actualizado");
            } else {
                Notification.show("Selecciona un gato");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {

            Long id = parseId(idField);

            if (id != null) {
                crudService.eliminar(Gato.class, id);
                limpiarFormulario.run();
                refrescar.run();

                Notification.show("Gato eliminado");
            } else {
                Notification.show("Selecciona un gato");
            }
        });

        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button(
                "Limpiar", e -> limpiarFormulario.run()
        );

        layout.add(
                form,
                crearAcciones(btnCrear, btnConsultar,
                        btnActualizar, btnEliminar, btnLimpiar),
                grid
        );

        return layout;
    }

    // ==========================================
    // 2. FOTOS
    // ==========================================

    private Component crearSeccionFotos() {

        VerticalLayout layout = new VerticalLayout();

        TextField idField = new TextField("ID Foto");
        TextField idGatoField = new TextField("ID Gato");
        TextField urlField = new TextField("URL");
        DateTimePicker fechaField = new DateTimePicker("Fecha de foto");
        Checkbox principalField = new Checkbox("Es principal");
        TextArea descripcionField = new TextArea("Descripción");

        idField.setReadOnly(true);

        FormLayout form = new FormLayout(
                idField,
                idGatoField,
                urlField,
                fechaField,
                principalField,
                descripcionField
        );

        Grid<Foto> grid = new Grid<>(Foto.class, false);

        grid.addColumn(Foto::getIdFoto).setHeader("ID Foto");
        grid.addColumn(Foto::getIdGato).setHeader("ID Gato");
        grid.addColumn(Foto::getUrl).setHeader("URL");
        grid.addColumn(Foto::getFechaFoto).setHeader("Fecha");
        grid.addColumn(Foto::getDescripcion).setHeader("Descripción");

        Runnable refrescar = () ->
                grid.setItems(crudService.listarTodos(Foto.class));

        Runnable limpiarFormulario = () -> {
            grid.asSingleSelect().clear();
            limpiar(idField, idGatoField, urlField,
                    fechaField, principalField, descripcionField);
        };

        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {

            Foto foto = e.getValue();

            if (foto != null) {
                ponerId(idField, foto.getIdFoto());
                idGatoField.setValue(numero(foto.getIdGato()));
                urlField.setValue(texto(foto.getUrl()));
                fechaField.setValue(foto.getFechaFoto());
                principalField.setValue(foto.isEsPrincipal());
                descripcionField.setValue(texto(foto.getDescripcion()));
            }
        });

        Button btnCrear = new Button("Crear", e -> {

            Long idGato = parseId(idGatoField);

            if (idGato == null) {
                Notification.show("Ingresa un ID de gato válido");
                return;
            }

            Foto foto = new Foto();

            foto.setIdGato(idGato);
            foto.setUrl(urlField.getValue());
            foto.setFechaFoto(fechaField.getValue());
            foto.setEsPrincipal(principalField.getValue());
            foto.setDescripcion(descripcionField.getValue());

            crudService.guardar(foto);
            refrescar.run();

            ponerId(idField, foto.getIdFoto());

            Notification.show("Foto guardada");
        });

        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {

            Long id = parseId(idField);

            Foto foto = id != null
                    ? crudService.buscarPorId(Foto.class, id)
                    : null;

            if (foto != null) {
                ponerId(idField, foto.getIdFoto());
                idGatoField.setValue(numero(foto.getIdGato()));
                urlField.setValue(texto(foto.getUrl()));
                fechaField.setValue(foto.getFechaFoto());
                principalField.setValue(foto.isEsPrincipal());
                descripcionField.setValue(texto(foto.getDescripcion()));

                Notification.show("Foto encontrada");
            } else {
                Notification.show("Foto no encontrada");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {

            Long id = parseId(idField);
            Long idGato = parseId(idGatoField);

            if (idGato == null) {
                Notification.show("ID de gato inválido");
                return;
            }

            Foto foto = id != null
                    ? crudService.buscarPorId(Foto.class, id)
                    : null;

            if (foto != null) {

                foto.setIdGato(idGato);
                foto.setUrl(urlField.getValue());
                foto.setFechaFoto(fechaField.getValue());
                foto.setEsPrincipal(principalField.getValue());
                foto.setDescripcion(descripcionField.getValue());

                crudService.actualizar(foto);
                refrescar.run();

                Notification.show("Foto actualizada");
            } else {
                Notification.show("Selecciona una foto");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {

            Long id = parseId(idField);

            if (id != null) {
                crudService.eliminar(Foto.class, id);
                limpiarFormulario.run();
                refrescar.run();

                Notification.show("Foto eliminada");
            } else {
                Notification.show("Selecciona una foto");
            }
        });

        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button(
                "Limpiar", e -> limpiarFormulario.run()
        );

        layout.add(
                form,
                crearAcciones(btnCrear, btnConsultar,
                        btnActualizar, btnEliminar, btnLimpiar),
                grid
        );

        return layout;
    }

    // ==========================================
    // 3. UBICACIONES
    // ==========================================

    private Component crearSeccionUbicaciones() {

        VerticalLayout layout = new VerticalLayout();

        TextField idField = new TextField("ID Ubicación");
        TextField direccionField = new TextField("Dirección");
        TextField barrioField = new TextField("Barrio");
        TextField ciudadField = new TextField("Ciudad");

        idField.setReadOnly(true);

        FormLayout form = new FormLayout(
                idField, direccionField, barrioField, ciudadField
        );

        Grid<Ubicacion> grid = new Grid<>(Ubicacion.class, false);

        grid.addColumn(Ubicacion::getIdUbicacion).setHeader("ID");
        grid.addColumn(Ubicacion::getDireccion).setHeader("Dirección");
        grid.addColumn(Ubicacion::getBarrio).setHeader("Barrio");
        grid.addColumn(Ubicacion::getCiudad).setHeader("Ciudad");

        Runnable refrescar = () ->
                grid.setItems(crudService.listarTodos(Ubicacion.class));

        Runnable limpiarFormulario = () -> {
            grid.asSingleSelect().clear();
            limpiar(idField, direccionField, barrioField, ciudadField);
        };

        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {

            Ubicacion ubicacion = e.getValue();

            if (ubicacion != null) {
                ponerId(idField, ubicacion.getIdUbicacion());
                direccionField.setValue(texto(ubicacion.getDireccion()));
                barrioField.setValue(texto(ubicacion.getBarrio()));
                ciudadField.setValue(texto(ubicacion.getCiudad()));
            }
        });

        Button btnCrear = new Button("Crear", e -> {

            Ubicacion ubicacion = new Ubicacion();

            ubicacion.setDireccion(direccionField.getValue());
            ubicacion.setBarrio(barrioField.getValue());
            ubicacion.setCiudad(ciudadField.getValue());

            crudService.guardar(ubicacion);
            refrescar.run();

            ponerId(idField, ubicacion.getIdUbicacion());

            Notification.show("Ubicación guardada");
        });

        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {

            Long id = parseId(idField);

            Ubicacion ubicacion = id != null
                    ? crudService.buscarPorId(Ubicacion.class, id)
                    : null;

            if (ubicacion != null) {
                ponerId(idField, ubicacion.getIdUbicacion());
                direccionField.setValue(texto(ubicacion.getDireccion()));
                barrioField.setValue(texto(ubicacion.getBarrio()));
                ciudadField.setValue(texto(ubicacion.getCiudad()));

                Notification.show("Ubicación encontrada");
            } else {
                Notification.show("Ubicación no encontrada");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {

            Long id = parseId(idField);

            Ubicacion ubicacion = id != null
                    ? crudService.buscarPorId(Ubicacion.class, id)
                    : null;

            if (ubicacion != null) {

                ubicacion.setDireccion(direccionField.getValue());
                ubicacion.setBarrio(barrioField.getValue());
                ubicacion.setCiudad(ciudadField.getValue());

                crudService.actualizar(ubicacion);
                refrescar.run();

                Notification.show("Ubicación actualizada");
            } else {
                Notification.show("Selecciona una ubicación");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {

            Long id = parseId(idField);

            if (id != null) {
                crudService.eliminar(Ubicacion.class, id);
                limpiarFormulario.run();
                refrescar.run();

                Notification.show("Ubicación eliminada");
            } else {
                Notification.show("Selecciona una ubicación");
            }
        });

        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button(
                "Limpiar", e -> limpiarFormulario.run()
        );

        layout.add(
                form,
                crearAcciones(btnCrear, btnConsultar,
                        btnActualizar, btnEliminar, btnLimpiar),
                grid
        );

        return layout;
    }

    // ==========================================
    // 4. AVISTAMIENTOS
    // ==========================================

    private Component crearSeccionAvistamientos() {

        VerticalLayout layout = new VerticalLayout();

        TextField idField = new TextField("ID Avistamiento");
        TextField idGatoField = new TextField("ID Gato");
        TextField idUserField = new TextField("ID Usuario");
        TextField idFotoField = new TextField("ID Foto");
        DatePicker fechaField = new DatePicker("Fecha");

        idField.setReadOnly(true);

        FormLayout form = new FormLayout(
                idField,
                idGatoField,
                idUserField,
                idFotoField,
                fechaField
        );

        Grid<Avistamiento> grid =
                new Grid<>(Avistamiento.class, false);

        grid.addColumn(Avistamiento::getIdAvistamiento)
                .setHeader("ID");
        grid.addColumn(Avistamiento::getIdGato)
                .setHeader("ID Gato");
        grid.addColumn(Avistamiento::getIdUser)
                .setHeader("ID Usuario");
        grid.addColumn(Avistamiento::getIdFoto)
                .setHeader("ID Foto");
        grid.addColumn(Avistamiento::getFecha)
                .setHeader("Fecha");

        Runnable refrescar = () ->
                grid.setItems(crudService.listarTodos(Avistamiento.class));

        Runnable limpiarFormulario = () -> {
            grid.asSingleSelect().clear();
            limpiar(idField, idGatoField, idUserField,
                    idFotoField, fechaField);
        };

        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {

            Avistamiento avistamiento = e.getValue();

            if (avistamiento != null) {
                ponerId(idField, avistamiento.getIdAvistamiento());
                idGatoField.setValue(numero(avistamiento.getIdGato()));
                idUserField.setValue(numero(avistamiento.getIdUser()));
                idFotoField.setValue(numero(avistamiento.getIdFoto()));
                fechaField.setValue(avistamiento.getFecha());
            }
        });

        Button btnCrear = new Button("Crear", e -> {

            Long idGato = parseId(idGatoField);
            Long idUser = parseId(idUserField);
            Long idFoto = parseId(idFotoField);

            if (idGato == null || idUser == null || idFoto == null) {
                Notification.show("Revisa los ID ingresados");
                return;
            }

            Avistamiento avistamiento = new Avistamiento();

            avistamiento.setIdGato(idGato);
            avistamiento.setIdUser(idUser);
            avistamiento.setIdFoto(idFoto);
            avistamiento.setFecha(fechaField.getValue());

            crudService.guardar(avistamiento);
            refrescar.run();

            ponerId(idField, avistamiento.getIdAvistamiento());

            Notification.show("Avistamiento guardado");
        });

        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {

            Long id = parseId(idField);

            Avistamiento avistamiento = id != null
                    ? crudService.buscarPorId(Avistamiento.class, id)
                    : null;

            if (avistamiento != null) {
                ponerId(idField, avistamiento.getIdAvistamiento());
                idGatoField.setValue(numero(avistamiento.getIdGato()));
                idUserField.setValue(numero(avistamiento.getIdUser()));
                idFotoField.setValue(numero(avistamiento.getIdFoto()));
                fechaField.setValue(avistamiento.getFecha());

                Notification.show("Avistamiento encontrado");
            } else {
                Notification.show("Avistamiento no encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {

            Long id = parseId(idField);
            Long idGato = parseId(idGatoField);
            Long idUser = parseId(idUserField);
            Long idFoto = parseId(idFotoField);

            if (idGato == null || idUser == null || idFoto == null) {
                Notification.show("Revisa los ID ingresados");
                return;
            }

            Avistamiento avistamiento = id != null
                    ? crudService.buscarPorId(Avistamiento.class, id)
                    : null;

            if (avistamiento != null) {

                avistamiento.setIdGato(idGato);
                avistamiento.setIdUser(idUser);
                avistamiento.setIdFoto(idFoto);
                avistamiento.setFecha(fechaField.getValue());

                crudService.actualizar(avistamiento);
                refrescar.run();

                Notification.show("Avistamiento actualizado");
            } else {
                Notification.show("Selecciona un avistamiento");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {

            Long id = parseId(idField);

            if (id != null) {
                crudService.eliminar(Avistamiento.class, id);
                limpiarFormulario.run();
                refrescar.run();

                Notification.show("Avistamiento eliminado");
            } else {
                Notification.show("Selecciona un avistamiento");
            }
        });

        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button(
                "Limpiar", e -> limpiarFormulario.run()
        );

        layout.add(
                form,
                crearAcciones(btnCrear, btnConsultar,
                        btnActualizar, btnEliminar, btnLimpiar),
                grid
        );

        return layout;
    }

    // ==========================================
    // 5. USUARIOS
    // ==========================================

    private Component crearSeccionUsuarios() {

        VerticalLayout layout = new VerticalLayout();

        TextField idField = new TextField("ID Usuario");
        TextField nombreField = new TextField("Nombre");
        TextField apellidoField = new TextField("Apellido");
        EmailField emailField = new EmailField("Email");
        TextField celularField = new TextField("Celular");
        PasswordField passwordField = new PasswordField("Contraseña");

        idField.setReadOnly(true);

        FormLayout form = new FormLayout(
                idField,
                nombreField,
                apellidoField,
                emailField,
                celularField,
                passwordField
        );

        Grid<User> grid = new Grid<>(User.class, false);

        grid.addColumn(User::getIdUser).setHeader("ID");
        grid.addColumn(User::getNombre).setHeader("Nombre");
        grid.addColumn(User::getApellido).setHeader("Apellido");
        grid.addColumn(User::getEmail).setHeader("Email");
        grid.addColumn(User::getCelular).setHeader("Celular");

        Runnable refrescar = () ->
                grid.setItems(crudService.listarTodos(User.class));

        Runnable limpiarFormulario = () -> {
            grid.asSingleSelect().clear();
            limpiar(idField, nombreField, apellidoField,
                    emailField, celularField, passwordField);
        };

        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {

            User user = e.getValue();

            if (user != null) {
                ponerId(idField, user.getIdUser());
                nombreField.setValue(texto(user.getNombre()));
                apellidoField.setValue(texto(user.getApellido()));
                emailField.setValue(texto(user.getEmail()));
                celularField.setValue(texto(user.getCelular()));
                passwordField.clear();
            }
        });

        Button btnCrear = new Button("Crear", e -> {

            if (passwordField.isEmpty()) {
                Notification.show("Escribe una contraseña");
                return;
            }

            User user = new User();

            user.setNombre(nombreField.getValue());
            user.setApellido(apellidoField.getValue());
            user.setEmail(emailField.getValue());
            user.setCelular(celularField.getValue());
            user.setPassword(passwordField.getValue());

            crudService.guardar(user);
            refrescar.run();

            ponerId(idField, user.getIdUser());
            passwordField.clear();

            Notification.show("Usuario guardado");
        });

        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {

            Long id = parseId(idField);

            User user = id != null
                    ? crudService.buscarPorId(User.class, id)
                    : null;

            if (user != null) {
                ponerId(idField, user.getIdUser());
                nombreField.setValue(texto(user.getNombre()));
                apellidoField.setValue(texto(user.getApellido()));
                emailField.setValue(texto(user.getEmail()));
                celularField.setValue(texto(user.getCelular()));
                passwordField.clear();

                Notification.show("Usuario encontrado");
            } else {
                Notification.show("Usuario no encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {

            Long id = parseId(idField);

            User user = id != null
                    ? crudService.buscarPorId(User.class, id)
                    : null;

            if (user != null) {

                user.setNombre(nombreField.getValue());
                user.setApellido(apellidoField.getValue());
                user.setEmail(emailField.getValue());
                user.setCelular(celularField.getValue());

                if (!passwordField.isEmpty()) {
                    user.setPassword(passwordField.getValue());
                }

                crudService.actualizar(user);
                refrescar.run();
                passwordField.clear();

                Notification.show("Usuario actualizado");
            } else {
                Notification.show("Selecciona un usuario");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {

            Long id = parseId(idField);

            if (id != null) {
                crudService.eliminar(User.class, id);
                limpiarFormulario.run();
                refrescar.run();

                Notification.show("Usuario eliminado");
            } else {
                Notification.show("Selecciona un usuario");
            }
        });

        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button(
                "Limpiar", e -> limpiarFormulario.run()
        );

        layout.add(
                form,
                crearAcciones(btnCrear, btnConsultar,
                        btnActualizar, btnEliminar, btnLimpiar),
                grid
        );

        return layout;
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================

    private Long parseId(TextField field) {
        try {
            return Long.parseLong(field.getValue().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String texto(String valor) {
        return valor != null ? valor : "";
    }

    private String numero(Long valor) {
        return valor != null ? valor.toString() : "";
    }

    private void ponerId(TextField campo, Long id) {
        campo.setValue(numero(id));
    }

    private void limpiar(HasValue<?, ?>... campos) {
        for (HasValue<?, ?> campo : campos) {
            campo.clear();
        }
    }

    private HorizontalLayout crearAcciones(Button... botones) {

        HorizontalLayout layout = new HorizontalLayout(botones);
        layout.getStyle().set("flex-wrap", "wrap");

        return layout;
    }
}
