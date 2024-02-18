package by.harlap.monitoring.in.listener;

import by.harlap.monitoring.facade.*;
import by.harlap.monitoring.in.controller.impl.*;
import by.harlap.monitoring.initialization.DependencyFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {

    /**
     * Runs Liquibase migrations when the application context is initialized.
     *
     * @param contextEvent the ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        final ServletContext context = contextEvent.getServletContext();

        final AuditFacade auditFacade = DependencyFactory.findFacade(AuditFacade.class);
        context.addServlet("AuditController", new AuditController(auditFacade)).addMapping("/audit");

        final AuthFacade authFacade = DependencyFactory.findFacade(AuthFacade.class);
        context.addServlet("AuthController", new AuthController(authFacade)).addMapping("/auth");

        final DeviceFacade deviceFacade = DependencyFactory.findFacade(DeviceFacade.class);
        context.addServlet("DeviceController", new DeviceController(deviceFacade)).addMapping("/devices");

        final MeterReadingInputFacade meterReadingInputFacade = DependencyFactory.findFacade(MeterReadingInputFacade.class);
        context.addServlet("MeterReadingsInputController", new MeterReadingsInputController(meterReadingInputFacade)).addMapping("/inputMeterReadings");

        final MeterReadingsRelevantInfoFacade meterReadingsRelevantInfoFacade = DependencyFactory.findFacade(MeterReadingsRelevantInfoFacade.class);
        context.addServlet("MeterReadingsRelevantInfoController", new MeterReadingsRelevantInfoController(meterReadingsRelevantInfoFacade)).addMapping("/relevantMeterReadings");

        final MeterReadingsMonthlyInfoFacade meterReadingsMonthlyInfoFacade = DependencyFactory.findFacade(MeterReadingsMonthlyInfoFacade.class);
        context.addServlet("MeterReadingsMonthlyInfoFacade", new MeterReadingsMonthlyInfoController(meterReadingsMonthlyInfoFacade)).addMapping("/meterReadingsForMonth");

        final MeterReadingsHistoryFacade meterReadingsHistoryFacade = DependencyFactory.findFacade(MeterReadingsHistoryFacade.class);
        context.addServlet("MeterReadingsHistoryController", new MeterReadingsHistoryController(meterReadingsHistoryFacade)).addMapping("/meterReadingsHistory");

        final RegisterFacade registerFacade = DependencyFactory.findFacade(RegisterFacade.class);
        context.addServlet("RegisterController", new RegisterController(registerFacade)).addMapping("/register");
    }
}