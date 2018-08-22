package com.ssitcloud.view.common.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.support.RequestContext;

import com.ssitcloud.common.entity.ResultEntity;


@RequestMapping(value={"lang"})
@Controller
public class LangController {

	//@Resource(name="localeResolver")
	//private LocaleResolver localeResolver;
	
	@RequestMapping(value={"changeLang"})
	public String  changeLang(HttpServletRequest request,HttpServletResponse response){
		ResultEntity result=new ResultEntity();
		/*String language=request.getParameter("lang");
        if(language==null||language.equals("")){
            return result;
        }else{
        	language=language.toLowerCase();
            if(language.equals("zh_cn")){
            	localeResolver.setLocale(request, response, Locale.CHINA );
            }else if(language.equals("en_us")){
            	localeResolver.setLocale(request, response, Locale.ENGLISH );
            }else{
            	localeResolver.setLocale(request, response, Locale.CHINA );
            }
        }*/
		String language=request.getParameter("locale");
		language=language.toLowerCase();
		if(language.equals("zh_cn")){
            Locale locale = new Locale("zh", "CN"); 
            System.out.println(locale.getDisplayLanguage());
            //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            (new CookieLocaleResolver()).setLocale (request, response, locale);
        }
        else if(language.equals("en_us")){
            Locale locale = new Locale("en", "US"); 
            //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            (new CookieLocaleResolver()).setLocale (request, response, locale);
        }
        else 
            //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,LocaleContextHolder.getLocale());
            (new CookieLocaleResolver()).setLocale (request, response, LocaleContextHolder.getLocale());
        
        //从后台代码获取国际化信息
        RequestContext requestContext = new RequestContext(request);
       // model.addAttribute("money", requestContext.getMessage("money"));
       // model.addAttribute("date", requestContext.getMessage("date"));

        
       // FormatModel formatModel=new FormatModel();

       // formatModel.setMoney(12345.678);
       // formatModel.setDate(new Date());
        
       // model.addAttribute("contentModel", formatModel);
        result.setState(true);
		
        return "/page/main";
	}
}
