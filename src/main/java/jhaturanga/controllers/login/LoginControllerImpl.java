package jhaturanga.controllers.login;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import jhaturanga.views.View;

public final class LoginControllerImpl implements LoginController {

    @Override
    public View getView() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean login(final String username, final String password) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean register(final String username, final String password, final String comfirmPassword) {
        // "res/user/user.json"

        final Map<String, String> mapOfUser = new HashMap<>();
        JSONArray arrayListUser;

        if (!password.equals(comfirmPassword)) { // two password equals
            return false;
        }

        final JSONParser parser = new JSONParser();
        try {
            arrayListUser = (JSONArray) parser.parse(new FileReader("res/user/user.json"));
            for (final Object o : arrayListUser) {
                final JSONObject person = (JSONObject) o;

                final String usernameFromFile = (String) person.get("username");
                final String passwordFromFile = (String) person.get("password");
                mapOfUser.put(usernameFromFile, passwordFromFile);

            }
        } catch (IOException | ParseException e) {
            arrayListUser = new JSONArray();
            e.printStackTrace();
        }

        if (mapOfUser.containsKey(username)) {
            return false;
        }

        mapOfUser.put(username, password);

        for (final Entry<String, String> userInfo : mapOfUser.entrySet()) {
            final JSONObject jsonUser = new JSONObject();
            jsonUser.put("username", userInfo.getKey());
            jsonUser.put("password", userInfo.getValue());
            arrayListUser.add(jsonUser);
        }

        try (FileWriter file = new FileWriter("res/user/user.json")) {

            file.write(arrayListUser.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(arrayListUser);

        return true;

    }

}
