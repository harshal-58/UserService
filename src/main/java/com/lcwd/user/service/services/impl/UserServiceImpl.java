package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
//import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generate  unique userid
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        //implement RATING SERVICE CALL: USING REST TEMPLATE
        return userRepository.findAll();
    }

    //get single user
//    @Override
//    public User getUser(String userId) {
//        //get user from database with the help  of user repository
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));
//        // fetch rating of the above  user from RATING SERVICE
//        //http://localhost:8083/ratings/users/47e38dac-c7d0-4c40-8582-11d15f185fad
//
//        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
//        logger.info("{} ", ratingsOfUser);
//        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
//        List<Rating> ratingList = ratings.stream().map(rating -> {
//            //api call to hotel service to get the hotel
//            //http://localhost:8082/hotels/1cbaf36d-0b28-4173-b5ea-f1cb0bc0a791
//            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            // logger.info("response status code: {} ",forEntity.getStatusCode());
//            //set the hotel to rating
//            rating.setHotel(hotel);
//            //return the rating
//            return rating;
//        }).collect(Collectors.toList());
//
//        user.setRatings(ratingList);
//
//        return user;
//    }
  
 /*   
  // Using RestTemplate -> Service Communication
  
    @Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceAccessException("User with given id is not found on server !! : "+userId));
		// fetch rating of the above user from RATING SERVICE
		// http://localhost:8083/ratings/users/910f5831-6452-4a3d-b7e0-810b2b2e6175
		
//		ArrayList<Rating> ratingsOfUsers = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), ArrayList.class);  // Here URL is hardCoded 
//		logger.info("{} ",ratingsOfUsers);
		
		Rating[] ratingsOfUsers = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);  // Here URL is hardCoded 
		logger.info("{} ",ratingsOfUsers);
		
		List<Rating> ratings = Arrays.stream(ratingsOfUsers).toList();
		
		List<Rating> ratingList = ratings.stream().map(rating -> {
			// api call to hotel service to get the hotel
			// http://localhost:8082/hotels/ebfee042-8edd-4bec-a3df-f1ad871a46e9
			
			ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
			Hotel hotel = forEntity.getBody();
			logger.info("response status code: {} ",forEntity.getStatusCode());
			// set the hotel to rating 
			rating.setHotel(hotel);
			// return the rating 
			 return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingList);
		
		return user;
    }
   */
    
    @Override
   	public User getUser(String userId) {
   		// TODO Auto-generated method stub
   		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceAccessException("User with given id is not found on server !! : "+userId));
   		// fetch rating of the above user from RATING SERVICE
   		// http://localhost:8083/ratings/users/910f5831-6452-4a3d-b7e0-810b2b2e6175
   		
//   		ArrayList<Rating> ratingsOfUsers = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), ArrayList.class);  // Here URL is hardCoded 
//   		logger.info("{} ",ratingsOfUsers);
   		
   		Rating[] ratingsOfUsers = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);  // Here URL is hardCoded 
   		logger.info("{} ",ratingsOfUsers);
   		
   		List<Rating> ratings = Arrays.stream(ratingsOfUsers).toList();
   		
   		List<Rating> ratingList = ratings.stream().map(rating -> {
   			// api call to hotel service to get the hotel
   			// http://localhost:8082/hotels/ebfee042-8edd-4bec-a3df-f1ad871a46e9
   			
   			Hotel hotel = hotelService.getHotel(rating.getHotelId());
   		//	logger.info("response status code: {} ",forEntity.getStatusCode());
   			// set the hotel to rating 
   			rating.setHotel(hotel);
   			// return the rating 
   			 return rating;
   		}).collect(Collectors.toList());
   		user.setRatings(ratingList);
   		
   		return user;
       }
    
}
