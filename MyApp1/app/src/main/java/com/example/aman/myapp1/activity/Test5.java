package com.example.aman.myapp1.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.model.CustAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Test5 extends ActionBarActivity {


    List<String> userEmails = new ArrayList<String>();
    List<CustAccount> custAccounts = new ArrayList<CustAccount>();
    ListView list;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test5);

        custAccounts = getUserAccounts();

        for(CustAccount custAccount : custAccounts){

            String possibleEmail = custAccount.getType()+" , "+custAccount.getName();
            Log.i("Test5","userEmail- "+custAccount.getName() +","+"userAccount type-"+ custAccount.getType());
            userEmails.add(possibleEmail);

        }

        if(userEmails != null && userEmails.size() > 0){
            Log.i("Test5 -- onCreate","userEmail list size- "+userEmails.size());
            list = (ListView) findViewById(R.id.test5_list_view);
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userEmails);
            list.setAdapter(adapter);

        }
    }

    private List<CustAccount> getUserAccounts(){
        CustAccount custAccount;
        List<CustAccount> custAccounts = new ArrayList<CustAccount>();

        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");

        for (Account account : accounts) {

            if (emailPattern.matcher(account.name).matches()) {
                custAccount = new CustAccount();
                Log.i("Test5--in getUser","userEmail- "+account.name +","+"userAccount type-"+ account.type);
                custAccount.setName(account.name);
                custAccount.setType(account.type);
                custAccounts.add(custAccount);

            }
        }

        return custAccounts;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test5, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
