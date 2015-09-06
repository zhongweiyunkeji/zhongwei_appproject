package com.cdhxqh.travel_ticket_app.ui.widget.hotelWineShop.base;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;


import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.ui.widget.hotelWineShop.utils.SignatureUtils;

// Http访问器
public class HttpAccessAdapter {
	// Http请求静态信息
	private static ArrayList<HttpRequestProperty> StaticHttpRequestProperty = LoadStaticHttpRequestProperties();
	private static String  allianceid="285578";
	private static String  sid="735704";
	private static String  apikey="5BA929B5-61A7-4FF6-8FD5-3FAA22908EA9";
	
	// 发送指定的请求到指定的URL上
	public static String SendRequestToUrl(String requestContent,
			String urlString, String paraName,  Activity activity) {
		InputStream errorStream = null;
		HttpURLConnection httpCon = null;
		try {
			URL url = new URL(urlString);
			String content = XMLToString(requestContent);
			String soapMessage = AddSoapShell(content, activity);
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setRequestMethod("POST");
			httpCon.setDoOutput(true);
			httpCon.setDoInput(true);

			for (int i = 0; i < StaticHttpRequestProperty.size(); i++) {
				httpCon.setRequestProperty(StaticHttpRequestProperty.get(i).getName(), 
						StaticHttpRequestProperty.get(i).getValue());
			}

			httpCon.setRequestProperty("Content-Length", String.valueOf(soapMessage.length()));


			OutputStream outputStream = httpCon.getOutputStream();
			outputStream.write(soapMessage.getBytes("utf-8"));
			outputStream.close();

			InputStream inputStream = httpCon.getInputStream();
			// String encoding = httpCon.getRequestProperty("Accept-Encoding");
			BufferedReader br = null;
			// httpCon.getResponseMessage();
			if (httpCon.getRequestProperty("Accept-Encoding") != null) {
				try {
					GZIPInputStream gzipStream = new GZIPInputStream(inputStream);
					br = new BufferedReader(new InputStreamReader(gzipStream, "utf-8"));
				} catch (IOException e) {
					br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
				}
			} else {
				br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			}
			StringBuffer result = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			return StringToXML(RemoveSoapShell(result.toString()));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			errorStream = httpCon.getErrorStream();
			if (errorStream != null) {
				String errorMessage = null;
				String line = null;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						errorStream));
				try {
					while ((line = br.readLine()) != null) {
						errorMessage += line;
					}
					return errorMessage;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			try {
				errorStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (SdkSystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpCon != null) {
				httpCon.disconnect();
			}
		}

		return "";
	}

	// 将Soap外壳添加到请求体上
	private static String AddSoapShell(
			/* String parameterName, */String patameterValue,   Activity activity) throws Exception {
		BufferedReader bufferedReader = null;
		try {
			InputStream in = activity.getResources().openRawResource(R.raw.request);

			bufferedReader = new BufferedReader(new InputStreamReader(in));
			String text = bufferedReader.readLine();
			StringBuilder soapShellStringBuilder = new StringBuilder();
			while (text != null) {
				soapShellStringBuilder.append(text);
				text = bufferedReader.readLine();
			}
			String result = soapShellStringBuilder.toString();
			return result.replaceAll("string", patameterValue);
		} catch (FileNotFoundException e) {
			throw new SdkSystemException("无法找到请求模板文件");
		} catch (IOException e) {
			throw new SdkSystemException("无法读取请求模板文件");
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	// 删除Soap外壳信息
	private static String RemoveSoapShell(String source) {
		String result = "";
		int indexElementBegin = source.indexOf("<RequestResult>");
		int indexElementEnd = source.indexOf("</RequestResult>");
		if (indexElementBegin > 0 && indexElementEnd > 0) {
			result = source.substring(indexElementBegin
					+ "<RequestResult>".length(), indexElementEnd);
		}
		return result;
	}

	// 将xml文档转换为可传输的字符串
	private static String XMLToString(String source) {
		String result = source.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		return result;
	}

	// 将返回的字符串转换为可反序列化XML文本
	private static String StringToXML(String source) {
		String result = source.replaceAll("&lt;", "<");
		result = result.replaceAll("&gt;", ">");
		return result;
	}

	// 加载静态信息
	private static ArrayList<HttpRequestProperty> LoadStaticHttpRequestProperties() {
		ArrayList<HttpRequestProperty> staticHttpRequestProperty = new ArrayList<HttpRequestProperty>();
		//staticHttpRequestProperty.add(new HttpRequestProperty("Host", "crmint.dev.sh.ctriptravel.com"));
		staticHttpRequestProperty.add(new HttpRequestProperty("Content-Type", "text/xml; charset=utf-8"));
		staticHttpRequestProperty.add(new HttpRequestProperty("SOAPAction", "http://ctrip.com/Request"));
		staticHttpRequestProperty.add(new HttpRequestProperty("Accept-Encoding", "gzip, deflate"));

		return staticHttpRequestProperty;
	}

	public static String getData(String requestType,String requestBody,String url, Activity activity){
		String request = createRequestXml(requestType,requestBody);
		return SendRequestToUrl(request, url, "requestXML", activity);
	}

	private static String createRequestXml(String requestType,String requestBody) {
		//请求开始
		StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		try {
			//请求头Header(固定)
			sb.append("<Request>");
			sb.append("<Header ");
			sb.append("AllianceID=\"" + allianceid + "\" ");
			sb.append("SID=\"" + sid + "\" ");
			long timestamp = SignatureUtils.GetTimeStamp();
			sb.append("TimeStamp=\"" + timestamp + "\" ");
			String signature = SignatureUtils.CalculationSignature(timestamp
					+ "", allianceid, apikey, sid, requestType);
			sb.append("Signature=\"" + signature + "\" ");
			sb.append("RequestType=\"" + requestType + "\"/>");
			
			//请求内容(动态)
			sb.append(requestBody);
			
			//请求结尾
			sb.append("</Request>");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return sb.toString();
	}

}
