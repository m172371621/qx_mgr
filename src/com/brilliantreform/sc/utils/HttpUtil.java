package com.brilliantreform.sc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.log4j.Logger;

public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);

	public static String doGet(String url, String requestContent) {

		return doGet(url + "?" + requestContent);
	}

	public static String doGet(String url) {

		logger.info("doGet: " + url);

		HttpGet httpGet = new HttpGet(url);

		DefaultHttpClient httpclient = new DefaultHttpClient();

		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);

		StringBuilder sb = new StringBuilder();

		try {
			HttpResponse resp = httpclient.execute(httpGet);

			if (resp.getStatusLine().getStatusCode() != 200) {

				logger.error("doGet: " + resp.getStatusLine().getStatusCode());
				return null;

			} else {

				HttpEntity entity = resp.getEntity();

				BufferedReader buReader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				String line = "";

				while ((line = buReader.readLine()) != null) {
					sb.append(line);
				}
			}

			logger.info("doGet: " + sb.toString().trim());


			return sb.toString().trim();


		} catch (ClientProtocolException e) {

			logger.error("doGet: " + e.getMessage());

		} catch (IOException e) {

			logger.error("doGet: " + e.getMessage());
		}

		logger.info("doPost end: " + sb.toString());

		return null;
	}
}
