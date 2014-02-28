package controller.action.net;

import controller.action.ActionType;
import controller.action.GCAction;
import controller.net.RobotWatcher;
import data.AdvancedData;
import data.PlayerInfo;
import data.SPLCoachMessage;

public class SPLCoachMessageReceived extends GCAction {
    public SPLCoachMessage message = new SPLCoachMessage();
    
    public SPLCoachMessageReceived(){
        super(ActionType.NET);
    }
    
    @Override
    public void perform(AdvancedData data) {
        byte team = (data.team[0].teamColor == message.team)? (byte)0 : (byte)1;
        RobotWatcher.updateCoach(team);
        if (!data.rejectCoachMessage[message.team] && (data.team[team].coach.penalty != PlayerInfo.PENALTY_SPL_COACH_MOTION)) {
            data.rejectCoachMessage[message.team] = true;
            data.timestampCoachPackage[message.team] = System.currentTimeMillis();
            data.splCoachMessageQueue.add(message);
        }
    }

    @Override
    public boolean isLegal(AdvancedData data) {
        return true;
    }

}