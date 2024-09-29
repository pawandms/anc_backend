package com.vod.media.controller;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.hazelcast.core.HazelcastInstance;
import com.test.sample.Ticker;
import com.vod.exception.MinioServiceException;
import com.vod.exception.UserException;
import com.vod.exception.ValidationException;
import com.vod.hls.service.HlsService;
import com.vod.media.model.AppUser;
import com.vod.media.service.AppUserService;
import com.vod.media.service.MediaService;
import com.vod.minio.MinioService;
import com.vod.oauth.model.User;
import com.vod.oauth.service.UserService;
import com.vod.oauth.vo.UserVo;
import com.vod.util.EnvProp;
import com.vod.util.HlsUtil;


@Controller
@RequestMapping("/public")
public class PublicController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EnvProp env;

	@Autowired
	private UserService userService;
	
	@Autowired
	private HlsUtil hlsUtil;

	@Autowired
	private HlsService hlsService;

	@Autowired
	private MinioService minioService; 
	
	@Autowired
	private MediaService mediaService;

	@Autowired
	private AppUserService appUserService;

	//@Autowired
	//private Connection natConnection;
	
	@Autowired
	private HazelcastInstance hazelcastInstance;
	
//	@Autowired
//	private TokenEndpoint tokenEndpoint;
	
//	@Autowired
//	private ClientDetailsService clientDetailsService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody UserVo request) 
	{
		ResponseEntity<?> response = null;
	    
        try {
        		
        	if( null == request)
        	{
        		throw new UserException("Request can not be null");
        	}
        	
        	UserVo result = userService.createUser(request);
        	
        	response =  new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e)
        {
        	if(!request.getErrors().isEmpty())
        	{
        		request.setErrorCode("SIGNUP_ERROR");
        		response =  new ResponseEntity<>(request, HttpStatus.INTERNAL_SERVER_ERROR);
        	}
        	else {
        		response =  new ResponseEntity<>("User creation Error Msg:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
        	}
        	
        	
        }
        
        return response;	
        
    }
	

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> signIn(@RequestBody UserVo request) 
	{
		ResponseEntity<?> response = null;
	    
        try {
        	
			/*
			 * ClientDetails authenticatedClient =
			 * clientDetailsService.loadClientByClientId("General");
			 * 
			 * UsernamePasswordAuthenticationToken authRequest = new
			 * UsernamePasswordAuthenticationToken("General", "General",
			 * authenticatedClient.getAuthorities());
			 * 
			 * Map<String, String> parameters = new HashMap<>();
			 * parameters.put("grant_type", "password"); parameters.put("username",
			 * request.getEmail()); parameters.put("password",request.getPassword());
			 * 
			 * 
			 * // TokenRequest tokenRequest =
			 * oAuth2RequestFactory.createTokenRequest(parameters, authenticatedClient); //
			 * OAuth2Request oAuth2Request =
			 * oAuth2RequestFactory.createOAuth2Request(authenticatedClient, tokenRequest);
			 * 
			 * 
			 * 
			 * 
			 * 
			 * response = tokenEndpoint.postAccessToken(authRequest, parameters);
			 * 
			 * 
			 * logger.info("Token EndPoint Class Name:"+response.getStatusCodeValue());
			 */        	
        		
        	if( null == request)
        	{
        		throw new UserException("Request can not be null");
        	}
        	
        	String result = "ok";
        	
        	//response =  new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e)
        {
        	if(!request.getErrors().isEmpty())
        	{
        		request.setErrorCode("SIGNUP_ERROR");
        		response =  new ResponseEntity<>(request, HttpStatus.INTERNAL_SERVER_ERROR);
        	}
        	else {
        		response =  new ResponseEntity<>("User creation Error Msg:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
        	}
        	
        	
        }
        
        return response;	
        
    }

	
	@RequestMapping(value = "/email/activation/{token}/{uid}", method = RequestMethod.POST)
    public ResponseEntity<?> activationEmail(
    		@PathVariable("token") String token,
    		@PathVariable("uid") String uid
    		) 
	{
		ResponseEntity<?> response = null;
	    
		UserVo result = null;
        try {
        		
        	
        	result = userService.activateUser(token, uid);
        	if(result.isValid())
        	{
        		response =  new ResponseEntity<>(result, HttpStatus.OK);	
        	}
        	else {
        		throw new ValidationException("Invalid Request");
        	}
        	
        	
        }
        catch (Exception e)
        {
        	if(!result.getErrors().isEmpty())
        	{
        		response =  new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        	}
        	else {
        		response =  new ResponseEntity<>("User validation Error Msg:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
        	}
        	
        	
        }
        
        return response;	
        
    }
	

	
	@RequestMapping(value = "/user/{id:.+}", method = RequestMethod.GET )
    public ResponseEntity<?> getDummyUserDetails(@PathVariable("id") String id)  
	{	ResponseEntity<?> response = null;
		try {
			 
	        	if( id != null )
	        	{
	        		User user = new User();
	        		user.setId(id);
	        		user.setUserName("Pawan");
	        		user.setEmail("pawan.suryawanshi@gmail.com");
	        		user.setMobile("9999999999");
	        		user.setGender("Male");
	        		user.setNickName("Pawan");
	        		response = ResponseEntity.ok().body(user);
	        		
	        		System.out.println("Dummy User for ID:"+id+", "+user.toString());
	        	}
		}
		catch (Exception e)
		{
			response =  new ResponseEntity<>("HLS Master PlayList Generation Error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
       
		return response;
    }
	

	@RequestMapping(value = "/ticker/{id:.+}", method = RequestMethod.GET )
    public ResponseEntity<?> getDummyTickerDetails(@PathVariable("id") String id)  
	{	ResponseEntity<?> response = null;
		try {
			 
	        	if( id != null )
	        	{
	        		Ticker ticker = new Ticker();
	        		ticker.setName(id);
	        		ticker.setSymbol("id");
	        		ticker.setPrice_usd(10.00);
	        		response = ResponseEntity.ok().body(ticker);
	        		
	        		
	        	}
		}
		catch (Exception e)
		{
			response =  new ResponseEntity<>("Ticker Generation Error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
       
		return response;
    }

	
    /* Commented on 16-02-21 to Test Minio Object Store Response
    @RequestMapping(value = "/hls/{mediaId}/{playlistName:.+}", method = RequestMethod.GET, produces = "application/x-mpegURL")
    public ResponseEntity<?> getTestHlsPlayList(@PathVariable("mediaId") String mediaId,
    		@PathVariable("playlistName") String playListName	)  
	{	ResponseEntity<?> response = null;
		String mplXML = null;
		try {
			 
	        	if( null != mediaId)
	        	{
	        		//mplXML = hlsUtil.getHlsPlayList(mediaId, playListName);
	        		mplXML = hlsService.getMediaPlayList(mediaId, playListName);
	        		//response =  new ResponseEntity<>(mplXML, HttpStatus.OK);
	        		response = ResponseEntity.ok().cacheControl(CacheControl.maxAge(10000,TimeUnit.SECONDS )).body(mplXML);
	        	}
		}
		catch ( HlsServiceException  e)
		{
			response =  new ResponseEntity<>("HLS Master PlayList Generation Error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
       
		logger.info(".........................................HLS MasterPlayList Generation Completed for  id:"+playListName);
		return response;
    }
    */
    
	
    
	/* Commented on 16-02-21 to Test Minio Object Store
    @GetMapping("/hls/{mediaId}/{segLocation}/{segFilename:.+}")
    public ResponseEntity<?> getTestSegmentStream(@PathVariable ("mediaId") String mediaId,
    		@PathVariable ("segLocation") String segLocation,
    		@PathVariable ("segFilename") String segFileName,
    		@RequestHeader(value = "Range", required = false) String httpRangeList) 
  {
    	logger.info("...Serving HLS Segmenet Stream for id:"+segFileName);
    
    	ResponseEntity<?> response = null;
	    try {
	    	
	    	//InputStream stream = hlsUtil.getSegmentSetream(mediaId, segLocation, segFileName);
	    	InputStream stream = hlsService.getSegmentStream(mediaId, segLocation, segFileName);
	    	InputStreamResource  streamResource = new InputStreamResource(stream);
        	response = ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
    		.cacheControl(CacheControl.maxAge(10000,TimeUnit.SECONDS ))
            .header(env.CONTENT_TYPE, "video/vnd.dlna.mpeg-tts")
            .body(streamResource);

        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        	response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return response;	
    
    
    }
    
	*/
	
	
	@RequestMapping(value = "/hls/{mediaId}/{playlistName:.+}", method = RequestMethod.GET, produces = "application/x-mpegURL")
    public ResponseEntity<?> getTestHlsPlayList(@PathVariable("mediaId") String mediaId,
    		@PathVariable("playlistName") String playListName	)  
	{	ResponseEntity<?> response = null;
		String mplXML = null;
		try {
	
			
	        	if( null != mediaId)
	        	{
	        		//logger.info(".........................................HLS MasterPlayList Generation Started for  id:"+playListName);
	        		
	        		//mplXML = hlsUtil.getHlsPlayList(mediaId, playListName);
	        		mplXML = minioService.getMediaPlayList(mediaId, playListName);
	        		//response =  new ResponseEntity<>(mplXML, HttpStatus.OK);
	        		response = ResponseEntity.ok().cacheControl(CacheControl.maxAge(10000,TimeUnit.SECONDS )).body(mplXML);
	        		logger.info(".........................................PlayList Generation Completed for  id:"+mediaId+" , Stream:"+playListName);
	        	}
		}
		catch (  MinioServiceException  e)
		{
			response =  new ResponseEntity<>("HLS Master PlayList Generation Error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
       
		
		return response;
    }
	
	
    @GetMapping("/hls/{mediaId}/{segLocation}/{segFilename:.+}")
    public ResponseEntity<?> getTestSegmentStream(@PathVariable ("mediaId") String mediaId,
    		@PathVariable ("segLocation") String segLocation,
    		@PathVariable ("segFilename") String segFileName,
    		@RequestHeader(value = "Range", required = false) String httpRangeList) 
  {
    //	logger.info("...Serving HLS Segmenet Stream for id:"+segFileName);
    
    	ResponseEntity<?> response = null;
	    try {
	    	
	    	//InputStream stream = hlsUtil.getSegmentSetream(mediaId, segLocation, segFileName);
	    	InputStream stream = minioService.getSegmentStream(mediaId, segLocation, segFileName);
	    	InputStreamResource  streamResource = new InputStreamResource(stream);
        	response = ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
    		.cacheControl(CacheControl.maxAge(10000,TimeUnit.SECONDS ))
            .header(env.CONTENT_TYPE, "video/vnd.dlna.mpeg-tts")
            .body(streamResource);

        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        	response =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return response;	
    
    
    }
    

    @GetMapping("/getmedia/{mediaId}/{dummyName:.+}")
    public ResponseEntity<?> getSteamingMedia(HttpServletRequest request, HttpServletResponse response, @RequestHeader(value = "Range", required = false) String httpRangeList,
    		@PathVariable ("mediaId") String mediaId,
    		@PathVariable ("dummyName") String contentID) 
  {
    	ResponseEntity<?> result = null;
	    try {
	    	
	    	result = mediaService.getMediaStream(mediaId, httpRangeList);
	    	logger.info("Serving Streming Data for Media:"+mediaId+" , For Range:"+httpRangeList);
        }
        catch (Exception e)
        {
        	logger.error(e.getMessage(), e);
        	result =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return result;	

  }
    
 
	@RequestMapping(value = "user/info", method = RequestMethod.POST)
    public ResponseEntity<?> addAppUser( @RequestBody AppUser user) 
	{
		ResponseEntity<?> response = null;
	    try {
	    	
	    	appUserService.addUpdateAppUserDetails(user);
	    	String msg = "AppUser details updated";
        	  response =  new ResponseEntity<>(msg, HttpStatus.OK);
        }
        catch ( Exception e)
        {
        	response =  new ResponseEntity<>("Update AppUser details error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        	

        }
        
        return response;	
    
	}

	@RequestMapping(value = "/user/{appUserID}/", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAppUser(
    		@PathVariable ("appUserID") String appUserID,
    		@RequestBody AppUser user) 
	{
		ResponseEntity<?> response = null;
	    try {
	    	
	    	appUserService.updateAppUserIpAddress(appUserID, user.getIpAddress());
	    	String msg = "AppUser details updated";
        	  response =  new ResponseEntity<>(msg, HttpStatus.OK);
        }
        catch ( Exception e)
        {
        	response =  new ResponseEntity<>("Update AppUser details error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        	

        }
        
        return response;	
    
	}
	
	
	/*
	
	@RequestMapping(value = "user/ping", method = RequestMethod.POST)
    public ResponseEntity<?> messageTesting() 
	{
		ResponseEntity<?> response = null;
	    try {

	    	Message msg = new Message();
	    	msg.setSender("Test1");
	    	msg.setReceiver("ALL");
	    	msg.setMessage("Hi from Test1");
	    	msg.setCreatedOn(new Date());
	   
	    	 // use Gson to encode the object to JSON
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String json = gson.toJson(msg);
            
	    	
	    	natConnection.publish("com.scaling-ws.updates",json.getBytes(StandardCharsets.UTF_8)); 		
	    	  response =  new ResponseEntity<>(msg, HttpStatus.OK);
        }
        catch ( Exception e)
        {
        	response =  new ResponseEntity<>("Update AppUser details error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        	

        }
        
        return response;	
    
	}

	*/

}