package org.vaadin.example.bookstore.ui.login;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import org.vaadin.example.bookstore.authentication.AccessControl;
import org.vaadin.example.bookstore.authentication.AccessControlFactory;
import org.vaadin.example.bookstore.ui.AdminView;
import org.vaadin.example.bookstore.ui.MainLayout;

/**
 * UI content when the user is not logged in yet.
 */
@Route("Login")
@PageTitle("Login")
@CssImport("./styles/shared-styles.css")
public class PantallaScreen extends FlexLayout {

    private AccessControl accessControl;

    public PantallaScreen() {
        accessControl = AccessControlFactory.getInstance().createAccessControl();
        buildUI();
    }

    
    
    
    private void buildUI() {
        setSizeFull();
        setClassName("login-screen");

        // login form, centered in the available part of the screen
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(this::login);
        loginForm.addForgotPasswordListener(
                event -> Notification.show("Hint: same as username"));
        
        // layout to center login form when there is sufficient screen space
        FlexLayout centeringLayout = new FlexLayout();
        centeringLayout.setSizeFull();
        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeringLayout.setAlignItems(Alignment.CENTER);
        centeringLayout.setFlexWrap(FlexWrap.WRAP);
        centeringLayout.setFlexDirection(FlexDirection.COLUMN);

        // information text about logging in
        Component loginInformation = buildLoginInformation();
        // START By Ayoub Nadir

        Button updateI18nButton = new Button("Switch to Spanish",
        		event -> loginForm.setI18n((LoginI18n) createSpanishI18n()));
        centeringLayout.add(loginForm);
        centeringLayout.add(updateI18nButton);
        

        //END
        add(loginInformation);
        add(centeringLayout);
    }
    
    private Component buildLoginInformation() {
        VerticalLayout loginInformation = new VerticalLayout();
        loginInformation.setClassName("login-information");
        
        //Start by Ayoub Nadir
        H1 loginInfoHeader = new H1("Iniciar sesion");
        loginInfoHeader.setClassName("login-infoHeader");
        
        Image logoDelSitio = new Image("icons/bookstoreLogoOf.png","alternative text");
        logoDelSitio.setClassName("logo-delSitio");
        
        Span loginInfoText = new Span(
                "nombre de usuario es \"admin\". tambien se puede iniciar la sesion con " +
                        "otro nombre pero tendra accesso 'Solo lectura'. Para todos los usuarioa " +
                        "La contraseña es la misma que el nombre de usuario");
        loginInfoText.setClassName("login-InfoText");
        loginInfoText.setWidth("100%");
        loginInformation.add(logoDelSitio);
        //loginInformation.add(loginInfoHeader);
        loginInformation.add(loginInfoText);
        return loginInformation;
    }
    // METHOD CREATEd BY AYOUB NADIR
    private Object createSpanishI18n() {
		// TODO Auto-generated method stub
    	// importado la clase LoginI18n para cambiar lengua de login
	    final LoginI18n i18n = LoginI18n.createDefault();

	    i18n.setHeader(new LoginI18n.Header());
	    i18n.getHeader().setTitle("Nombre de la aplicación");
	    i18n.getHeader().setDescription("Descripción de la aplicación");
	    i18n.getForm().setUsername("Usuario");
	    i18n.getForm().setTitle("Iniciar session");
	    i18n.getForm().setSubmit("Entrar");
	    i18n.getForm().setPassword("Contraseña");
	    i18n.getForm().setForgotPassword("Olvido mi contraseña");
	    i18n.getErrorMessage().setTitle("Usuario/contraseña invalidos");
	    i18n.getErrorMessage()
	        .setMessage("Confirma tu usuario o contraseña o intenta nuevamente");
	    i18n.setAdditionalInformation(
	        "Si necesita presentar información adicional al usuario \"\r\n"
	        + "+ \"(como credenciales estándar), este es el lugar.");
	    
	    return i18n;
	}

	

    private void login(LoginForm.LoginEvent event) {
        if (accessControl.signIn(event.getUsername(), event.getPassword())) {
            getUI().get().navigate("");
        } else {
            event.getSource().setError(true);
        }
    }
}
