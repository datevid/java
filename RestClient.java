import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestClient {
    private static Logger logger = Logger.getLogger(RestClient.class);

    public static  <T> T post(String url, Object data, Class<T> classOfResponse) throws Exception {
        String dataJson = "";
        try {
            Gson gson = new Gson();
            dataJson = gson.toJson(data);
            logger.debug("url");
            logger.debug(url);
            logger.debug("dataJson");
            logger.debug(dataJson);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> request = new HttpEntity<>(dataJson, headers);
            T t = restTemplate.postForObject(url, request, classOfResponse);
            dataJson = gson.toJson(t);
            logger.debug("response");
            logger.debug(dataJson);
            return t;
        }
        catch (HttpServerErrorException | HttpClientErrorException httpExc) {
            HttpStatus status = httpExc.getStatusCode();
            //if (status.equals(412)) {
            //    throw new Exception("No se puede registrar en el Repositorio como nuevo, porque ya existe");
            //}
            throw httpExc;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception("Ocurrió un error durante la petición REST " + url + " con data " + dataJson, e);
        }
    }

    public static <T> T postMultipart(String url, FileSystemResource file, Class<T> classOfResponse) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", file);
    
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            // return restTemplate.postForEntity(url, requestEntity, classOfResponse);
            return restTemplate.postForObject(url, requestEntity, classOfResponse);
        }
        catch (HttpServerErrorException | HttpClientErrorException httpExc) {
            throw httpExc;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception("Ocurrió un error durante la petición REST " + url + ", al subir un archivo", e);
        }
    }

    public static <T> T  get(String url, Object data, Class<T> classOfResponse) throws Exception {
        String dataJson = new Gson().toJson(data);
        try {
            long time_start, time_end;
            time_start = System.currentTimeMillis();
            RestTemplate restTemplate = new RestTemplate();
            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
            interceptors.add(new ClientHttpRequestInterceptor() {
                @Override
                public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                    request.getHeaders().set("Accept", MediaType.APPLICATION_JSON_VALUE);
                    return execution.execute(request, body);
                }
            });
            restTemplate.setInterceptors(interceptors);
            T objectResponse = restTemplate.getForObject(url, classOfResponse);
            time_end = System.currentTimeMillis();
            double result = ((time_end - time_start) / 1000.0);
            logger.info("La respuesta de " + url + " demoró:  " + result + " segundos");
            return objectResponse;
        } catch (Exception e) {
            throw new Exception("Ocurrió un error durante la petición REST " + url + " con data " + dataJson, e);
        }
    }

    public static <T> List<T>  getList(String url, Object data, Class<T[]> classOfResponse) throws Exception {
        String dataJson = new Gson().toJson(data);
        try {
            long time_start, time_end;
            time_start = System.currentTimeMillis();
            RestTemplate restTemplate = new RestTemplate();
            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
            interceptors.add(new ClientHttpRequestInterceptor() {
                @Override
                public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                    request.getHeaders().set("Accept", MediaType.APPLICATION_JSON_VALUE);
                    return execution.execute(request, body);
                }
            });
            restTemplate.setInterceptors(interceptors);
            T[] objectResponse = restTemplate.getForObject(url, classOfResponse);
            time_end = System.currentTimeMillis();
            double result = ((time_end - time_start) / 1000.0);
            logger.info("La respuesta de " + url + " demoró:  " + result + " segundos");
            return  Arrays.asList(objectResponse);
        } catch (Exception e) {
            throw new Exception("Ocurrió un error durante la petición REST " + url + " con data " + dataJson, e);
        }
    }

    public static  <T> T post2(String url, String dataJson, Class<T> classOfResponse) throws Exception {
        //String dataJson = "";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            //dataJson = gson.toJson(data);
            System.out.println("url");
            System.out.println(url);
            System.out.println("dataJson");
            System.out.println(dataJson);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> request = new HttpEntity<>(dataJson, headers);
            //T t = restTemplate.postForObject(url, request, classOfResponse);
            //ResponseEntity<String> response;
            //response = restTemplate.postForObject(url, request, String.class);

            ResponseEntity<String> response;
            //response = restTemplate.postForObject(url, HttpMethod.GET, request, String.class);
            response = restTemplate.exchange(url,HttpMethod.POST,request,String.class);

            System.out.println("response:");
            System.out.println(response);
            return null;
        }
        catch (HttpServerErrorException | HttpClientErrorException e) {
            HttpStatus status = e.getStatusCode();
            //if (status != HttpStatus.NOT_FOUND) {
            //    throw e;
            //}
            throw e;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception("Ocurrió un error durante la petición REST " + url + " con data " + dataJson, e);
        }
    }

    /**
     * obtiene el archivo de una url y lo guarda en la ruta indicada
     * Ver: https://www.javacodemonk.com/download-a-file-using-spring-resttemplate-75723d97
     * @param url
     * @param pathStr
     * @return
     * @throws Exception
     */
    public static boolean getAndSaveFile(String url, String pathStr) throws Exception {
        long time_start, time_end;
        RestTemplate restTemplate = new RestTemplate();
        byte[] imageBytes = null;
        Path fullPath;

        //get
        try {
            //time ini
            time_start = System.currentTimeMillis();
            imageBytes = restTemplate.getForObject(url, byte[].class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Ocurrió un error durante la petición REST " + url, e);
        }

        //save file
        try {
            //time end
            //fullPath = Paths.get(pathStr);
            //Files.write(fullPath, imageBytes);

            File tmpFile = new File(pathStr);
            FileOutputStream fos = new FileOutputStream(tmpFile);
            fos.write(imageBytes);
            fos.flush();
            fos.close();

            time_end = System.currentTimeMillis();
            double result = ((time_end - time_start) / 1000.0);
            logger.info("La respuesta de " + url + " demoró:  " + result + " segundos");
            System.out.println("La respuesta de " + url + " demoró:  " + result + " segundos");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Ocurrió un error durante guardado el archivo",e);

        }
    }
    
    /**
     * Incluye timeout tiempo en segundos
     * ver: https://howtodoinjava.com/spring-boot2/resttemplate/resttemplate-timeout-example/
     * @param url
     * @param data
     * @param classOfResponse
     * @param <T>
     * @return
     * @throws Exception
     */
    public static  <T> T post(String url, Object data, Class<T> classOfResponse, int timeout) throws Exception {
        String dataJson = "";
        try {
            Gson gson = new Gson();
            dataJson = gson.toJson(data);
            logger.debug("url");
            logger.debug(url);
            logger.debug("dataJson");
            logger.debug(dataJson);
            SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
            s.setReadTimeout(timeout*1000);
            s.setConnectTimeout(timeout*1000);
            RestTemplate restTemplate = new RestTemplate(s);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> request = new HttpEntity<>(dataJson, headers);
            T t = restTemplate.postForObject(url, request, classOfResponse);
            dataJson = gson.toJson(t);
            logger.debug("response");
            logger.debug(dataJson);
            return t;
        }
        catch (HttpServerErrorException | HttpClientErrorException httpExc) {
            HttpStatus status = httpExc.getStatusCode();
            //if (status.equals(412)) {
            //    throw new Exception("No se puede registrar en el Repositorio como nuevo, porque ya existe");
            //}
            throw httpExc;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception("Ocurrió un error durante la petición REST " + url + " con data " + dataJson, e);
        }
    }

}
