package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;

/**
 * The MainMenuController class represents the main menu of the application.
 * It provides options for the user to navigate to different functionalities such as
 * obtaining meter readings, entering meter readings, viewing readings for a specific month,
 * viewing the history of meter readings, and exiting the user profile or the entire program.
 */
public class MainMenuController extends AbstractController {

    /**
     * Constructs a new MainMenuController with the specified initialization data.
     *
     * @param initializationData The data needed for initializing the controller.
     */
    public MainMenuController(AbstractController.InitializationData initializationData) {
        super(initializationData);
    }

    /**
     * Displays the main menu options to the user and handles user input to navigate to different functionalities.
     * The user can choose to obtain meter readings, enter meter readings, view readings for a specific month,
     * view the history of meter readings, exit the user profile, or exit the entire program.
     */
    @Override
    public void show() {
        boolean finished = false;
        final Role activeRole = context.getActiveUser().getRole();

        do {
            console.print("Главное меню");
            console.print("1. Получить актуальные показания счётчика");

            if (activeRole.equals(Role.USER)) {
                console.print("2. Внести показания счётчика");
            } else {
                console.print("2. Просмотреть аудит системы");
            }

            console.print("3. Вывести показания счётчика за конкретный месяц");
            console.print("4. Вывести историю подачи показаний");
            console.print("5. Выйти из профиля");
            console.print("6. Покинуть программу");

            final int mainPageActionCode = console.readInt();

            switch (mainPageActionCode) {
                case 1 -> dispatcher.getController(MeterReadingsRelevantInfoController.class).show();
                case 2 -> {
                    if (activeRole.equals(Role.USER)) {
                        dispatcher.getController(MeterReadingsInputController.class).show();
                    } else {
                        dispatcher.getController(AuditController.class).show();
                    }
                }
                case 3 -> dispatcher.getController(MeterReadingsMonthlyInfoController.class).show();
                case 4 -> dispatcher.getController(MeterReadingsHistoryController.class).show();
                case 5 -> finished = true;
                case 6 -> handleExit();
                default -> handleUnknownAction();
            }
        } while (!finished);
    }
}
