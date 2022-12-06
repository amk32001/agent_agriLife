package authenticate;

public class AgentDetails
{
    public String fullName,email,aadharNo,dob;

    public AgentDetails()
    {

    }

    public AgentDetails(String fullName,String email,String aadharNo,String dob)
    {
        this.fullName=fullName;
        this.email=email;
        this.aadharNo=aadharNo;
        this.dob=dob;
    }
}
