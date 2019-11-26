public class Payload {

    public static String resource(String zipCode)
    { String resource= "weather/forecast/"+zipCode+"/tomorrow/coolest/hour";
      return resource;
    }
    public static String host()
    {
        String host="http://localhost:8080/";
        return host;
    }
}
