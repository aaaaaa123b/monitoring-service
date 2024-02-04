package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.MainMenuController;
import by.harlap.monitoring.initialization.DependencyFactory;

/**
 * Controller class for the main menu specific to an administrator.
 */
public class AdminMainMenuController extends MainMenuController {

    /**
     * Constructor for AdminMainMenuController.
     *
     * @param initializationData The initialization data for the controller.
     */
    public AdminMainMenuController(InitializationData initializationData) {
        super(initializationData);
    }

    /**
     * Displays the main menu options for an administrator user and handles user input.
     */
    @Override
    public void show() {
        boolean finished = false;

        do {
            console.print("Главное меню");
            console.print("1. Получить актуальные показания счётчика");
            console.print("2. Просмотреть аудит системы");
            console.print("3. Вывести показания счётчика за конкретный месяц");
            console.print("4. Вывести историю подачи показаний");
            console.print("5. Внести новый вид счётчика");
            console.print("6. Выйти из профиля");
            console.print("7. Покинуть программу");

            final int mainPageActionCode = console.readInt();

            switch (mainPageActionCode) {
                case 1 -> DependencyFactory.findController(MeterReadingsRelevantInfoController.class).show();
                case 2 -> DependencyFactory.findController(AuditController.class).show();
                case 3 -> DependencyFactory.findController(MeterReadingsMonthlyInfoController.class).show();
                case 4 -> DependencyFactory.findController(MeterReadingsHistoryController.class).show();
                case 5 -> DependencyFactory.findController(AddNewDeviceController.class).show();
                case 6 -> finished = true;
                case 7 -> handleExit();
                default -> handleUnknownAction();
            }
        } while (!finished);
    }

    /**
     * Returns the role corresponding to this controller, which is ADMIN for AdminMainMenuController.
     *
     * @return The corresponding role.
     */
    @Override
    public Role getCorrespondingRole() {
        return Role.ADMIN;
    }
}
