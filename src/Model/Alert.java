package Model;

import Model.Enums.RoleType;

public class Alert {

    private String msgHeader;
    private String msgBody;


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


}
