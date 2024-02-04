package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.MainMenuController;
import by.harlap.monitoring.initialization.DependencyFactory;

/**
 * Controller class for the main menu specific to a user.
 */
public class UserMainMenuController extends MainMenuController {

    /**
     * Constructor for UserMainMenuController.
     *
     * @param initializationData The initialization data for the controller.
     */
    public UserMainMenuController(AbstractController.InitializationData initializationData) {
        super(initializationData);
    }

    /**
     * Displays the main menu options for a regular user and handles user input.
     */
    @Override
    public void show() {
        boolean finished = false;

        do {
            console.print("Главное меню");
            console.print("1. Получить актуальные показания счётчика");
            console.print("2. Внести показания счётчика");
            console.print("3. Вывести показания счётчика за конкретный месяц");
            console.print("4. Вывести историю подачи показаний");
            console.print("5. Выйти из профиля");
            console.print("6. Покинуть программу");

            final int mainPageActionCode = console.readInt();

            switch (mainPageActionCode) {
                case 1 -> DependencyFactory.findController(MeterReadingsRelevantInfoController.class).show();
                case 2 -> DependencyFactory.findController(MeterReadingsInputController.class).show();
                case 3 -> DependencyFactory.findController(MeterReadingsMonthlyInfoController.class).show();
                case 4 -> DependencyFactory.findController(MeterReadingsHistoryController.class).show();
                case 5 -> finished = true;
                case 6 -> handleExit();
                default -> handleUnknownAction();
            }
        } while (!finished);
    }

    /**
     * Returns the role corresponding to this controller, which is USER for UserMainMenuController.
     *
     * @return The corresponding role.
     */
    @Override
    public Role getCorrespondingRole() {
        return Role.USER;
    }
}
