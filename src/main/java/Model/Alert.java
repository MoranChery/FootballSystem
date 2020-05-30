package Model;

import Model.Enums.RoleType;

import java.util.UUID;

public class Alert {

    private String alertId;
    private String msgHeader;
    private String msgBody;

    public Alert(String msgHeader, String msgBody) {
        alertId = UUID.randomUUID().toString();
        this.msgHeader = msgHeader;
        this.msgBody = msgBody;
    }
    @Override
    public String toString() {
        return "header:\n" + msgHeader + "\n"+
                "body: \n" + msgBody;
    }


    //getter and setters
    public String getMsgHeader() {
        return msgHeader;
    }
    public void setMsgHeader(String msgHeader) {
        this.msgHeader = msgHeader;
    }
    public String getMsgBody() {
        return msgBody;
    }
    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }
    public String getAlertId() {
        return alertId;
    }
    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }


}
