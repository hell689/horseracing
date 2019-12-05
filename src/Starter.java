
import domain.Bet;
import domain.BetType;
import domain.Runner;
import domain.User;
import service.BetService;
import service.UserService;
import util.ServiceFactory;
import util.ServiceFactoryImpl;

import java.util.Date;
import java.util.List;

public class Starter {
    public static void main(String[] args){
    	
/*    	Bet bet = new Bet();
        Runner runner = new Runner();
        User user = new User();
        try {
        	bet.setAmount(5L);
        	runner.setId(1L);
        	user.setId(1L);
        	bet.setBetType(BetType.values()[Integer.parseInt("0")]);
        } catch (NumberFormatException e) {}
        bet.setUser(user);
        bet.setRunner(runner);
        bet.setBetTime(new Date());*/

        try(ServiceFactory service = new ServiceFactoryImpl()){
        	UserService userService = service.getUserService();
            List<User> users = userService.findAll();
            System.out.println(users);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
