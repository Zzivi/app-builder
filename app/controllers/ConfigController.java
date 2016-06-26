package controllers;

import models.Config;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.editFormConfig;
import views.html.index;

import javax.inject.Inject;

/**
 * Created by dgarcia on 26/06/2016.
 */
public class ConfigController extends Controller {
    private FormFactory formFactory;

    @Inject
    ConfigController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result edit(String appName) {
        Config config = new Config();
        config.load(appName);
        Form<Config> configForm = formFactory.form(Config.class).fill(config);
        return ok(editFormConfig.render(appName, configForm));
    }

    public Result update(String appName) {
        Form<Config> configForm = formFactory.form(Config.class).bindFromRequest();

        Config config = new Config();
        config.setDriverNum(configForm.get().getDriverNum());
        config.setSslConnection(configForm.get().getSslConnection());
        config.setSupportNum(configForm.get().getSupportNum());

        config.save();

        return ok(index.render("Config updated for " + appName +
                " driverNum: "+ config.getDriverNum() +
                " sslConnection: " + config.getSslConnection() +
                " supportNum: " + config.getSupportNum()
        ));
    }
}
