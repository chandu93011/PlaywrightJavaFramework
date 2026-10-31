package tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minidev.json.writer.JsonReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class DataProviderDemoTest  {



    //Same work with three different ways ,the Json method is best we can change when ever we want in the
    // Json will run accordingly

    @DataProvider(name="basicData")  //multi Dimensional array of objects
    public Object[][] basicData(){
        return new Object[][] {{"cy93011@gmail.com","Chandan@123445566"},{"chandan@gmail.com","Chandan@1234"},{"chandan@pbpay.com","Test@1234"} };
    }

    @Test(dataProvider = "basicData")
    public void testFillForm(String email,String password){
        System.out.println(email);
        System.out.println(password);
    }

    @DataProvider(name="hasMapData")  //multi Dimensional array of objects
    public Object[][] hashMapData(){
        HashMap<String,String> user1=new HashMap<>();
        user1.put("email","cy93011@gmail.com");
        user1.put("password","Chandan@123445566");

        HashMap<String,String> user2=new HashMap<>();
        user2.put("email","chandan@gmail.com");
        user2.put("password","Chandan@1234");

        HashMap<String,String> user3=new HashMap<>();
        user3.put("email","chandan@pbpay.com");
        user3.put("password","Test@1234");

        return new Object[][] {{user1},{user2},{user3} };
    }

    //reading the data form the json and passing as hashmap

@DataProvider(name = "jsonData")
public Object[][] jsonData() throws IOException {
    String jsonContent = new String(Files.readAllBytes(
            Paths.get("src/main/resources/testData_tc1.json")));

    Type type = new TypeToken<List<HashMap<String, String>>>() {}.getType();
    List<HashMap<String, String>> list = new Gson().fromJson(jsonContent, type);

    Object[][] table = new Object[list.size()][1];
    for (int i = 0; i < list.size(); i++) {
        table[i][0] = list.get(i);
    }
    return table;
}


    @Test(dataProvider ="jsonData")
    public  void testWithHashMap(HashMap<String,String> data){

        System.out.println(data.get("email"));
        System.out.println(data.get("password"));

    }

}
