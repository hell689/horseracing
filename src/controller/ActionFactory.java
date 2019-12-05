package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import controller.bet.BetListAction;
import controller.bet.BetSaveAction;
import controller.horse.HorseEditAction;
import controller.horse.HorseListAction;
import controller.horse.HorseSaveAction;
import controller.race.RaceEditAction;
import controller.race.RaceListAction;
import controller.race.RaceSaveAction;
import controller.runner.RunnerDeleteAction;
import controller.runner.RunnerEditAction;
import controller.runner.RunnerFixedAction;
import controller.runner.RunnerListAction;
import controller.runner.RunnerSaveAction;
import controller.user.UserAddBalanceAction;
import controller.user.UserChangeBalanceAction;
import controller.user.UserListAction;

public class ActionFactory {
    private static Map<String, Class<? extends Action>> actions = new HashMap<>();
    
    static {
        //actions.put("/", MainAction.class);
        actions.put("/horse/list", HorseListAction.class);
        actions.put("/horse/edit", HorseEditAction.class);
        actions.put("/horse/save", HorseSaveAction.class);
        actions.put("/race/list", RaceListAction.class);
        actions.put("/race/edit", RaceEditAction.class);
        actions.put("/race/save", RaceSaveAction.class);
        actions.put("/runner/save", RunnerSaveAction.class);
        actions.put("/runner/list", RunnerListAction.class);
        actions.put("/runner/edit", RunnerEditAction.class);
        actions.put("/runner/delete", RunnerDeleteAction.class);
        actions.put("/runner/fixed", RunnerFixedAction.class);
        actions.put("/login", LoginAction.class);
        actions.put("/logout", LogoutAction.class);
        actions.put("/bet/save", BetSaveAction.class);
        actions.put("/bet/list", BetListAction.class);
        actions.put("/user/addbalance", UserAddBalanceAction.class);
        actions.put("/user/changebalance", UserChangeBalanceAction.class);
        actions.put("/user/list", UserListAction.class);
    }
    
    public static Action getAction(String url) throws ServletException {
        Class<?> action = actions.get(url);
        if(action != null) {
            try {
                return (Action)action.newInstance();
            } catch(InstantiationException | IllegalAccessException | NullPointerException e) {
                throw new ServletException(e);
            }
        } else {
            return null;
        }
    }

}
