package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;
    @Mock
    private JavaMailSender javaMailSender;
    @Captor
    private ArgumentCaptor<SimpleMailMessage> simpleMailMessage;

    @Test
    @DisplayName("Deve enviar um email")
    void enviarEmail() {

        //Arrange

        String to = "exemplo@mail.com";
        String subject = "subject";
        String text = "text";

        //Act

        emailService.enviarEmail(to, subject, text);

        //Assert

        BDDMockito.then(javaMailSender).should().send(simpleMailMessage.capture());
        SimpleMailMessage email = simpleMailMessage.getValue();
        Assertions.assertEquals(to, email.getTo()[0]);
        Assertions.assertEquals(subject, email.getSubject());
        Assertions.assertEquals(text, email.getText());

    }

}