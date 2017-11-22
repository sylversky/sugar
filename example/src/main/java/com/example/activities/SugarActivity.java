package com.example.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.R;
import com.example.model.Address;
import com.example.model.Person;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SugarActivity extends Activity {

    private TextView textView;
    private Button buttonView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textView = (TextView) findViewById(R.id.textView);
        buttonView = (Button) findViewById(R.id.buttonView);
        buttonView.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Person person = new Person();
            person.setId(1l);
            person.setRegId("qwertyuiop");
            person.setName("human");
            person.setDob(new Date());

            Address addr1 = new Address();
            addr1.setId(1l);
            addr1.setStreet("Office building");
            addr1.setProvince("my lovely province");

            Address addr2 = new Address();
            addr2.setId(2l);
            addr2.setStreet("Apartment building");
            addr2.setProvince("my lovely countryside province");

            List addrList1 = new ArrayList();
            addrList1.add(addr1);
            addrList1.add(addr2);

            person.setAddress(addrList1);
            person.setReadStatus(0);

            show("First data", person);
            SugarRecord.insertOrUpdate(person);
            List<Person> people = SugarRecord.findAll(Person.class);
            Person p = people.get(0);
            show("After insert", p);

            p.setReadStatus(1);
            SugarRecord.update(p);

            List<Person> people1 = SugarRecord.findAll(Person.class);
            Person p1 = people1.get(0);
            show("After read status update", p1);

            Person person2 = new Person();
            person2.setId(2l);
            person2.setRegId("qwertyuiop");
            person2.setName("people");
            person2.setDob(new Date());

            Address addr3 = new Address();
            addr3.setId(1l);
            addr3.setStreet("home sweet home");
            addr3.setProvince("my lovely province");

            Address addr4 = new Address();
            addr4.setId(2l);
            addr4.setStreet("home comfortnest home");
            addr4.setProvince("my lovely countryside province");

            List<Address> addrList2 = new ArrayList<>();
            addrList2.add(addr3);
            addrList2.add(addr4);

            person2.setAddress(addrList2);
            person2.setReadStatus(2);

            SugarRecord.insertOrUpdate(person2);
            List<Person> people2 = SugarRecord.findAll(Person.class);
            Person p3 = people2.get(0);
            show("cant replace read status", p3);

        }
    };


    private void show(String title, Person person){
        StringBuffer stringBuffer = new StringBuffer(title);
        stringBuffer.append("\n");
        if(person!=null) {
            stringBuffer.append(person.toString());
        }
        stringBuffer.append("\n");
        textView.append(stringBuffer.toString());
    }
}
