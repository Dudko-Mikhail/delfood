package by.dudko.webproject.util.mail;

public class MailThreadFactory {
    public static Thread createConfirmRegistrationSender(StringBuffer controllerUrl, String recipient,
                                                         String verificationCode) {
        String message = MailTemplates.compileConfirmRegistrationTemplate(controllerUrl, recipient, verificationCode);
        return new Thread(() -> {
            MailSender sender = MailSender.getInstance();
            sender.send(recipient, "Authorization", message);
        });
    }
}
