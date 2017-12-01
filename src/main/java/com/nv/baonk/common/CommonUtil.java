package com.nv.baonk.common;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.nv.baonk.security.SecurityConfigBaonk;
import com.nv.baonk.service.UserService;
import com.nv.baonk.vo.User;


@Component
public class CommonUtil {
	
	public static final String PT_BASIC = "basic";
	public static final String PT_STANDARD = "standard";
	
	@Autowired
    private SecurityConfigBaonk securityConfBaonk;
	
	@Autowired
	private UserService userService;
	
	/*
	@Resource(name="EzOrganService")
	private EzOrganService ezOrganService;	

	
	/* File separator 공통 함수 */
	public String separator = "/";
	
	public final String CRLF = "\r\n";
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	public User getUserInfo(String loginCookie) {
		try {
			//logger.debug("LoginCookie in CommonUtil: " + loginCookie);			
			String decData = securityConfBaonk.decryptAES(loginCookie);			
			String[] decDataArray = decData.split("\\+");			
			
			String serverName = decDataArray[0];
			String userID = decDataArray[1];
			String userPassword = decDataArray[2];
			int tenantId = Integer.parseInt(decDataArray[3]);
			
			logger.debug("Server Name: " + serverName + " || User ID: " + userID + " || User Password: " + userPassword + " || Tenant ID: " + tenantId);

			User user = userService.findUserByUseridAndTenantid(userID, tenantId);	
			return user;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*public LoginSimpleVO userInfoSimple(String loginCookie) {
		try{
			String decData = egovFileScrty.decryptAES(loginCookie);

			String[] decDataArray = decData.split("///");
			
			String serverName = decDataArray[0];
			String userID = decDataArray[1];
			String locale = decDataArray[5];
			String lang = decDataArray[6];
			String timeZone = decDataArray[7];
			
            String tenantIdStr = "0";
            
            if (decDataArray.length >= 9) {
                tenantIdStr = decDataArray[8];	
            }
            
            LoginSimpleVO user = new LoginSimpleVO();
            user.setId(userID);
            user.setTenantId(Integer.parseInt(tenantIdStr));
            user.setLang(lang);
            user.setLocale(new Locale(locale));
			user.setOffset(timeZone);			
			user.setServerName(serverName);
			
			return user;
		}catch(Exception e){
			return null;
		}
	}
	
	public LoginVO aprUserInfo(String loginCookie) {
		try{
			logger.debug("aprUserInfo started");
			
			LoginVO user = userInfo(loginCookie);
			
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			
			HttpServletRequest request = sra.getRequest();
			
			Cookie[] cookie = request.getCookies();
			
			for (int k = 0; k < cookie.length; k++) {
				switch (cookie[k].getName()) {
				case "APRUI0":
					user.setDeptID(cookie[k].getValue());
					break;
				case "APRUI1":
					user.setDeptName1(URLDecoder.decode(cookie[k].getValue(), "utf-8"));
					break;
				case "APRUI2":
					user.setDeptName2(URLDecoder.decode(cookie[k].getValue(), "utf-8"));
					break;
				case "APRUI3":
					user.setCompanyID(URLDecoder.decode(cookie[k].getValue(), "utf-8"));
					break;
				case "APRUI4":
					user.setCompanyName2(URLDecoder.decode(cookie[k].getValue(), "utf-8"));
					break;
				case "APRUI5":
					user.setTitle(URLDecoder.decode(cookie[k].getValue(), "utf-8"));
					break;
				case "APRUI6":
					user.setTitle2(URLDecoder.decode(cookie[k].getValue(), "utf-8"));
					break;
				case "APRUI7":
					user.setCompanyID(cookie[k].getValue());
					break;
				}
			}
			
			if (user.getPrimary().equals("1")) {
				user.setTitle(user.getTitle());
				user.setDeptName(user.getDeptName1());
				user.setDisplayName(user.getDisplayName());
				user.setCompanyID(user.getCompanyID());
			} else {
				user.setTitle(user.getTitle2());
				user.setDeptName(user.getDeptName2());
				user.setDisplayName(user.getDisplayName2());
				user.setCompanyID(user.getCompanyName2());
			}
			
			logger.debug("aprUserInfo ended");
			
			return user;
		}catch(Exception e){
			return null;
		}
	}
	
	public LoginVO checkAdmin(String loginCookie){
		try{
			LoginVO user = userInfo(loginCookie);
	
			if (user.getRollInfo().indexOf("c=1") == -1 && user.getRollInfo().indexOf("k=1") == -1){
				return null;
			}else{
				return user;
			}
		}catch(Exception e){
			return null;
		}
	}
	
	public LoginVO aprCheckAdmin(String loginCookie){
		try{
			LoginVO user = aprUserInfo(loginCookie);
	
			if (user.getRollInfo().indexOf("c=1") == -1 && user.getRollInfo().indexOf("k=1") == -1){
				return null;
			}else{
				return user;
			}
		}catch(Exception e){
			return null;
		}
	}
	public List<String> getUserIdAndPassword(String loginCookie) {
		try{
			String decData = egovFileScrty.decryptAES(loginCookie);
			List<String> returnObject = new ArrayList<String>();
			
			String userId = decData.split("///")[1];
			String pass = decData.split("///")[4];
			returnObject.add(userId);
			returnObject.add(pass);
	
			return returnObject;
		}catch(Exception e){
			return null;
		}
	}	
		
	public static String getEncodedFileNameForDownload(String userAgentValue, String filename) {
		try {
			// in case of IE & Edge
			// the filename needs to be UTF-8 and URL-encoded.
			// URI class is more appropriate than URLEncoder class for this purpose.
			if (userAgentValue.contains("Trident") || userAgentValue.contains("Edge")) {
			    // "자동회신:"과 같이 :이 제목에 포함되어 있는 경우 메일 저장하기 시, 한글파일명 깨지는 문제가 있어
			    // :를 %3A로 변경한 후 URI 인코딩을 수행함. 
				filename = filename.replaceAll(":", "%3A");
				URI uri = new URI(null, null, filename, null);
				filename = uri.toASCIIString();
				// %3A에서 %가 %25로 인코딩되므로 다시 %3A로 변경함.
				filename = filename.replaceAll("%253A", "%3A");
			}
			// in case of Chrome, Safari
			// the filename consists of UTF-8 encoded bytes.
			else {
				filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}

		return filename;
	}
	
	public static void addXUACompatibleHeaderToResponse(HttpServletRequest request, HttpServletResponse response) {
		String browser = ClientUtil.getClientInfo(request, "browser");
		String compatibleValue = null;
		
		if (browser.equals("Edge") || browser.equals("IE11")) {
			compatibleValue = "IE=edge";
		} else if (browser.equals("IE10")) {
			compatibleValue = "IE=10";
        } else if (browser.equals("IE9")) {
            compatibleValue = "IE=9";
		} else if (browser.equals("IE8")) {
			compatibleValue = "IE=8";
		}
		
		if (compatibleValue != null) {
			response.setHeader("X-UA-Compatible", compatibleValue);
		}		
	}
	
	public boolean isLoginCookieExists(HttpServletRequest request, HttpServletResponse response) {
        boolean isCookie = false;     
        Cookie[] cookies = request.getCookies();
         session time을 위한 처리 주석 
        //HttpSession session = request.getSession(false);
        
        //if (session != null) {
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if("loginCookie".equals(cookie.getName())){
	                    //접속한 클라이언트 IP
	                    String ip = ClientUtil.getClientIP(request);
	                    String cValue = "";
	                    try {
	                        //쿠기에 저장되어 있는 IP
	                        cValue = egovFileScrty.decryptAES(cookie.getValue());
	
	                        if(cValue.split("///")[3].equals(ip)){                  
	                            isCookie = true;
	                        }
	                    } catch (Exception e) {
	                        //e.printStackTrace();
	                    }
	                }
	            }
	        }
        } else {
        	if (cookies != null) {
        		for (Cookie cookie : cookies) {
        			if(!cookie.getName().equals("saveid") && !cookie.getName().matches("POPUP_.*")){
        				cookie.setMaxAge(0);
        				cookie.setPath("/");
        				response.addCookie(cookie);
        			}
        	    }
        	}
        }        
        return isCookie;
	}
	
	public Document convertStringToDocument(String xmlStr) {
		String replaceData = xmlStr.trim().replaceFirst("^([\\W]+)<","<");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;
        Document doc = null;
        
        try {  
            builder = factory.newDocumentBuilder();  
            doc = builder.parse(new InputSource(new StringReader(replaceData)));
        } catch (Exception e) {}
        
        return doc;
	}
	
	public Document convertRequestToDocument(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();		
        String readData = "";
        BufferedReader br;
        Document doc = null;
        
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			
			while ((readData = br.readLine()) != null ) {
	            sb.append(readData);
	        }
			doc = convertStringToDocument(sb.toString());
			
		} catch(Exception e){}
		
		return doc;		
	}
	
	public String convertDocumentToString(Document doc){
		try{
			TransformerFactory tf = TransformerFactory.newInstance();
		    Transformer transformer = tf.newTransformer();
		    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		    StringWriter writer = new StringWriter();
		    transformer.transform(new DOMSource(doc), new StreamResult(writer));
		    String output = writer.getBuffer().toString();	    
			
			return output;
		}catch(Exception e){
			return null;
		}
	}
	
	// 객체의 필드 목록을 XML 형식으로 반환한다.
	// 필드명을 태그명으로 필드값을 태그 사이의 값으로 구성한다.
	public String getQueryResult(Object vo) throws Exception {
		StringBuilder stb = new StringBuilder();		
		
		if (vo != null) {
			stb.append("<ROW>");
			
			for (Field field : vo.getClass().getDeclaredFields()) {
		        field.setAccessible(true);
				String data = String.valueOf(field.get(vo));
	
				if (data == null || data.equals(null) || data.equals("null")) {
					data = "";
				}		
				
		        stb.append("<" + field.getName().toUpperCase() + ">");
		        stb.append(cleanValue(data));
		        stb.append("</" + field.getName().toUpperCase() + ">");		        
		    }
			
			stb.append("</ROW>");
		} else {
			stb.append("");
		}

		return stb.toString();
	}
	
	
	 * 행이 여러 개일 때 여러 행이 포함된 XML String 생성
	 * xmlTag: "<DATA>" 또는 다름 Tag
	 
	public String getQueryResult(List<Object> vo, String xmlTag) throws Exception{
		StringBuilder stb = new StringBuilder();		
		
		if (vo == null) {
			stb.append("");
			return stb.toString();
		}
		
	    stb.append("<DATA>");
	    
	    for (int i = 0; i < vo.size(); i++) {
			stb.append("<ROW>");
			
			for(Field field : vo.get(i).getClass().getDeclaredFields()){
		        field.setAccessible(true);
				String data = String.valueOf(field.get(vo.get(i)));
	
				if(data == null || data.equals(null) || data.equals("null")){
					data = "";
				}				
		        stb.append("<" + field.getName().toUpperCase() + ">");
		        stb.append(cleanValue(data));
		        stb.append("</" + field.getName().toUpperCase() + ">");		        
		    }
			stb.append("</ROW>");
		}
		stb.append("</DATA>");
		
		return stb.toString();
	}
	

	public String getMultiData(String lang, int tenantID) throws Exception{
		if (!lang.equals(commonService.getTenantConfig("PrimaryLang", tenantID))) {
			return "2";
		} else {
			return "";
		}
	}

	public String getPrimaryData(String lang, int tenantID) throws Exception {
		if (lang.equals(commonService.getTenantConfig("PrimaryLang", tenantID))) {
			return "1";
		} else {
			return "2";
		}
	}

	
	public String getLangData(String lang){
		if (lang.equals("1")) {
			return "";
		} else {
			return lang;
		}
	}	
	
	public String cleanValue(String pOrgString) {
		String value = ""; 
				
		if (pOrgString != null) {
			value = pOrgString.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
	        value = value.replaceAll("'", "&#39;");
	        value = value.replaceAll("\"", "&quot;");
	        value = value.replaceAll("eval\\((.*)\\)", "");
	        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");        
	        //value = value.replaceAll("script", "");
		}

		return value;
	}
	
	// 2016.09.06 by kgs: Property value의 값을 변환
	public String cleanPropertyValue(String pOrgString) {
		String value = ""; 
				
		if(pOrgString != null){
			value = pOrgString.replaceAll("&", "&amp;").replaceAll("\"", "&quot;");
		}

		return value;
	}
	
	public String trimDoubleQuotes(String src) {
		if (src.startsWith("\"") && src.endsWith("\"")) {
			src = src.substring(1, src.length() - 1);
		}		
		
		return src;
	}
	
	public boolean checkIE(HttpServletRequest request) {
		if (request.getHeader("User-Agent").indexOf("rv:11") > 0 || request.getHeader("User-Agent").indexOf("Trident/7.0") > 0) {
			return true;
		} else if ( request.getHeader("User-Agent").indexOf("Chrome") > 0) {
			return false;
		} else {
			return false;
		}
	}
	
	public String isoUTFDate(String dateTimeStr) throws Exception {
        String resultStr = "";

        if (dateTimeStr != null && !dateTimeStr.trim().equals("")){
            if (dateTimeStr.indexOf(" ") != -1){
                resultStr = dateTimeStr.split(" ")[0] + "T" + dateTimeStr.split(" ")[1] + ".000Z";
            } else{
                resultStr = dateTimeStr + "T00:00:00.000Z";
            }
        } else{
            resultStr = "";
        }
        
        return resultStr;
    }
	
	public String getRealPath(HttpServletRequest request) {
		String realPath = request.getServletContext().getRealPath("");
		
		if (realPath.substring(realPath.length() - 1).equals(separator)) {
			realPath = realPath.substring(0, realPath.length() - 1);
		} else if (realPath.substring(realPath.length() - 1).equals("\\")) {
			realPath = realPath.substring(0, realPath.length() - 1);
		}
		
		return realPath;
	}
	
	public String getUploadPath(String property, int tenantId) {
		return separator + "fileroot" + separator + tenantId + config.getProperty(property);
	}
	
	*//**
	 * <pre>
	 * timeZoneToUTC가 true면 TimeZone Date 문자열을 UTC타임 Date 문자열로 바꿔서 반환한다.
	 * timeZoneToUTC가 false면 UTC타임 Date 문자열을 TimeZone Date 문자열로 바꿔서 반환한다.
	 * - dateStr 형식 : yyyy-MM-dd HH:mm:ss, yyyy-MM-dd HH:mm, yyyy-MM-dd
	 * 				   yyyy/MM/dd HH:mm:ss, yyyy/MM/dd HH:mm, yyyy/MM/dd, yyMMdd
	 * - offset 형식 : ex) 235|+09:00
	 * </pre>
	 *//*
	public String getDateStringInUTC(String dateStr, String offset, boolean timeZoneToUTC) {
//		logger.debug("dateStr=" + dateStr + ", offset=" + offset + ", timeZoneToUTC=" + timeZoneToUTC);
		
		if (dateStr == null) {
			logger.error("dateStr is null.");
			return null;
		}
		
		if (offset == null || offset.indexOf("|") == -1) {
			logger.error("offset is null or offset format is wrong.");
			return dateStr;
		}
		
		String pattern = "";
		if (dateStr.length() == 8) {
			pattern = "yyyyMMdd";
		} else if (dateStr.length() == 10) {
			if (dateStr.indexOf("/") > -1) {
				pattern = "yyyy/MM/dd";
			} else {
				pattern = "yyyy-MM-dd";
			}
		} else if (dateStr.length() == 16) {
			if (dateStr.indexOf("/") > -1) {
				pattern = "yyyy/MM/dd HH:mm";
			} else {
				pattern = "yyyy-MM-dd HH:mm";
			}
		} else if (dateStr.length() == 21) {
			if (dateStr.indexOf("/") > -1) {
				pattern = "yyyy/MM/dd aa h:mm:ss";
			} else {
				pattern = "yyyy-MM-dd aa h:mm:ss";
			}
		} else {
			if (dateStr.indexOf("/") > -1) {
				pattern = "yyyy/MM/dd HH:mm:ss";
			} else {
				pattern = "yyyy-MM-dd HH:mm:ss";
			}
		}
//		logger.debug("pattern=" + pattern);
		
		String[] offsetArr = offset.split("\\|");
		
		SimpleDateFormat userFormat = new SimpleDateFormat(pattern);
		userFormat.setTimeZone(TimeZone.getTimeZone("GMT" + offsetArr[1]));
		
		SimpleDateFormat utcFormat = new SimpleDateFormat(pattern);
		utcFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		String resultDateStr = "";
		try {
			if (timeZoneToUTC) {
				resultDateStr = utcFormat.format(userFormat.parse(dateStr));
			} else {
				resultDateStr = userFormat.format(utcFormat.parse(dateStr));
			}
		} catch (ParseException e) {
			logger.error("Check the dateStr format.");
			return dateStr;
		}
		
//		logger.debug("resultDateStr=" + resultDateStr);
		return resultDateStr;
	}
	
	*//**
	 * 현재시간 UTC로 가져오기
	 * @param format 공백이면 기본 "yyyy-MM-dd HH:mm:ss" 형식
	 * @return 포맷팅된 UTC 현재시간 가져옴
	 * @throws Exception
	 *//*
	public String getTodayUTCTime(String format) throws Exception {
		logger.debug("getTodayUTCTime started");
		
		ZoneId utc = ZoneId.of("UTC");
		ZonedDateTime getTime = ZonedDateTime.of(LocalDateTime.now(utc), utc);
		
		DateTimeFormatter formatter = null;
		
		if (format == null || format.equals("")) {
			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		} else {
			try {
				formatter = DateTimeFormatter.ofPattern(format);
			} catch (Exception e) {
				logger.error("formatter error :: " + e.getMessage());
				formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			}
		}
		
		String today = getTime.format(formatter);
		
		logger.debug("getTodayUTCTime ended");
		
		return today;
	}
	
	*//**
	 * offset 시간을 분으로 변환하는 함수	 
	 *//*
	public String getMinuteUTC(String offSet) throws Exception {
		logger.debug("getMinuteUTC started");
		
		String format = offSet.split("\\|")[1];
		String cal = format.substring(0,1);
		int min = Integer.parseInt(format.substring(1,3)) * 60 + Integer.parseInt(format.substring(4,6));
		String time = cal + min;	
		
		return time;
	}
	
	public String makeDate (String year, String month, String day, boolean startFlag) {
		String result = "";
		
		if (month.length() == 1) {
			month = "0" + month;
		}
		
		if (!year.equals("") && !month.equals("") && !day.equals("")) {
			result = year + "-" + month + "-" + day;
			
			if (startFlag) {
				result += " 00:00:00";
			} else {
				result += " 23:59:59";
			}
		}
		return result;
	}
	
	public String getTwoLetterLangFromLangNum(String langNum) {
		String returnValue = "";
		
		if (langNum == null) {
			logger.error("langNum is null.");
			return null;
		}
		
		if (langNum.equals("1")) {
			returnValue = "ko";
		} else if (langNum.equals("2")) {
			returnValue = "en";
		} else if (langNum.equals("3")) {
			returnValue = "ja";
		} else if (langNum.equals("4")) {
			returnValue = "zh";
		} else {
			logger.error("Invalid langNum.");
		}
		
		return returnValue;
	}
	
	public String getLangNumFromTwoLetterLang(String twoLetterLang) {
		String returnValue = "";
		
		if (twoLetterLang == null) {
			logger.error("twoLetterLang is null.");
			return null;
		}
		
		if (twoLetterLang.equalsIgnoreCase("ko")) {
			returnValue = "1";
		} else if (twoLetterLang.equalsIgnoreCase("en")) {
			returnValue = "2";
		} else if (twoLetterLang.equalsIgnoreCase("ja")) {
			returnValue = "3";
		} else if (twoLetterLang.equalsIgnoreCase("zh")) {
			returnValue = "4";
		} else {
			logger.error("Invalid twoLetterLang.");
		}
		
		return returnValue;
	}
	
	public String makeListField(String orgStr) {
		if (orgStr == null || orgStr.equals("NULL") || orgStr.equals("null")) {
			return "";
		} else {
			return orgStr;
		}
	}
	
	public String byteCalculation(String bytes) {
        String retFormat = "0";
        Double size = Double.parseDouble(bytes);

        String[] s = { "bytes", "KB", "MB", "GB", "TB", "PB" };       

        if (!bytes.equals("0")) {
              int idx = (int) Math.floor(Math.log(size) / Math.log(1024));
              DecimalFormat df = new DecimalFormat("#,###.##");
              double ret = ((size / Math.pow(1024, Math.floor(idx))));
              retFormat = df.format(ret) + " " + s[idx];
         } else {
              retFormat += " " + s[0];
         }

         return retFormat;
	}

 baonk added
	public String getPackageType(int tenantId) throws Exception {
		String packageType = "standard";
		
		String licenseKey = ezCommonService.getTenantConfig("LicenseKey", tenantId);
		
		logger.debug("licenseKey=" + licenseKey);
		
		if (!licenseKey.equals("")) {
			try {
				// 라이센스키를 복호화한다.
				licenseKey = egovFileScrty.decryptAES(licenseKey);
				
				logger.debug("Decrypted licenseKey=" + licenseKey);
				
				String items[] = licenseKey.split(":");

				if (items.length >= 3) {
					packageType = items[2];					
				}
			} catch (Exception e) {
				logger.debug("License Key Decryption failed.");
			}			
		}
		
		logger.debug("packageType=" + packageType);
		
		return packageType;
	}	

    public void resetLoginFailAttempts(String userID, int tenantID) throws Exception{
    	String userLoginFailedAttempt = commonService.getUserConfigInfo(tenantID, userID, "LoginFailCount"); 
    	
		if (userLoginFailedAttempt.equals("")) {
			//User hasn't logged in fail yet
			return;
		} else {
			//Reset the number to 0
			commonService.updateUserConfigInfo(tenantID, userID, "LoginFailCount", "0");
		}
    }*/
	
	public void writeUploadedFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;
		String stordFilePathReal = (stordFilePath==null?"":stordFilePath);
		int BUFF_SIZE = 4096;
		
		try {
		    stream = file.getInputStream();
		    File cFile = new File(stordFilePathReal);
	
		    if (!cFile.isDirectory()) {
				boolean _flag = cFile.mkdirs();
				if (!_flag) {
				    throw new IOException("Directory creation Failed ");
				}
		    }
	
		    bos = new FileOutputStream(stordFilePathReal + File.separator + newName);
	
		    int bytesRead = 0;
		    byte[] buffer = new byte[BUFF_SIZE];
	
		    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
		    	bos.write(buffer, 0, bytesRead);
		    }
		} catch (FileNotFoundException fnfe) {
			logger.debug("fnfe: {}", fnfe);
		} catch (IOException ioe) {
			logger.debug("ioe: {}", ioe);
		} catch (Exception e) {
			logger.debug("e: {}", e);
		} finally {
		    if (bos != null) {
				try {
				    bos.close();
				} catch (Exception ignore) {
					logger.debug("IGNORED: {}", ignore.getMessage());
				}
		    }
		    if (stream != null) {
				try {
				    stream.close();
				} catch (Exception ignore) {
					logger.debug("IGNORED: {}", ignore.getMessage());
				}
		    }
		}
    }

	
}