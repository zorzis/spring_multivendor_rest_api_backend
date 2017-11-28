package com.multimerchant_haze.rest.v1.modules.emails.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import com.multimerchant_haze.rest.v1.app.errorHandling.AppException;
import com.multimerchant_haze.rest.v1.modules.orders.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import freemarker.template.Configuration;

/**
 * Help found here: http://websystique.com/spring/spring-4-email-using-velocity-freemaker-template-library/
 *
 * Created by zorzis on 11/2/2017.
 */


@Service("orderEmailService")
public class OrderMailServiceImpl implements MailService
{

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Override
    public void sendEmail(Object object) throws AppException
    {

        OrderDTO orderDTO = (OrderDTO)object;
        MimeMessagePreparator preparator = getOrderMessagePreparator(orderDTO);

        try
        {
            mailSender.send(preparator);
            System.out.println("Message has been sent.............................");
        } catch (MailException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    public MimeMessagePreparator getOrderMessagePreparator(final OrderDTO orderDTO)
    {

        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {

            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setSubject("haze.gr - New order #[" + orderDTO.getOrderID() + "]");
                helper.setFrom("info@haze.gr");
                helper.setTo(orderDTO.getClientDetails().getOrderClientEmail());

                Map<String, Object> model = new HashMap<String, Object>();
                model.put("orderDTO", orderDTO);


                String text = geFreeMarkerTemplateContent(model);//Use Freemarker
                System.out.println("Template content : " + text);

                // use the true flag to indicate you need a multipart message
                helper.setText(text, true);

                //Additionally, let's add a resource as an attachment as well.
                //helper.addAttachment("cutie.png", new ClassPathResource("linux-icon.png"));

            }
        };
        return preparator;
    }

    private String geFreeMarkerTemplateContent(Map<String, Object> model) throws AppException
    {
        StringBuffer content = new StringBuffer();
        try
        {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate("order_confirmation_email_template.ftl"), model));
            return content.toString();
        } catch (Exception e)
        {
            System.out.println("Exception occured while processing fmtemplate:" + e.getMessage());
        }
        return "";
    }

}