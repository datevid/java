public String restServiceSpringGeneralGet(String urlRest, int timeout) {
    String response = "";
    URI uri = UriComponentsBuilder.fromUriString(urlRest).build().encode().toUri();        
    try {            
        SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
        timeout=timeout*1000;
        s.setReadTimeout(timeout);
        s.setConnectTimeout(timeout);
        RestTemplate restTemplate = new RestTemplate(s);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        long time_start, time_end;
        time_start = System.currentTimeMillis();
        response = restTemplate.getForObject(uri, String.class);
        time_end = System.currentTimeMillis();
        double result = ((time_end - time_start) / 1000.0);
        logger.info("La respuesta de " + urlRest + " demoró:  " + result + " segundos");
        if (response != null) {
            logger.info("Respuesta obtenida del servicio:  " + response);                                
        }
    } catch (HttpStatusCodeException e) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {                
            try {                    
                response = e.getResponseBodyAsString();
                if (response != null) {                        
                    logger.warn("404 Retornado por Servicio. Message: "+response+", URL:"+ urlRest);
                }
            } catch (Exception ex) {
                logger.error("Error Http 404 en Servicio." + urlRest);
            }
        } else {
            logger.error("Error HTTP en Servicio. "+urlRest);
        }
    } catch(ResourceAccessException e){
        logger.error("Excepción en Servicio. "+urlRest);
    }catch (Exception e) {
        logger.error("Excepción en Servicio. "+urlRest, e);
    }
    return response;
}


public CiudadanoReniecRes getCiudadanoReniec(CiudadanoReniecReq ciudadanoReniecReq) {
    CiudadanoReniecRes response = null;
    try {
        Gson gson = new Gson();
        logger.info("request CiudadanoReniecReq: \n"+gson.toJson(ciudadanoReniecReq));
        String url = applicationProperties.getReniecRestUrlCiudadano();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        long time_start, time_end;
        time_start = System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String ciudadanoReniecReqJSON = gson.toJson(ciudadanoReniecReq);
        HttpEntity<String> entity = new HttpEntity<>(ciudadanoReniecReqJSON, headers);
        response = restTemplate.postForObject(url, entity, CiudadanoReniecRes.class);
        //ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        time_end = System.currentTimeMillis();
        double result = ((time_end - time_start) / 1000.0);
        logger.info("La consulta en " + url + ", demoró: " + result + " segundos");
        return response;
    } catch (HttpStatusCodeException e) {
        e.printStackTrace();
        logger.error(e.getMessage(),e);
        logger.error(response);
        return null;
    }
}
