package com.example.test_bottom_navbar.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test_bottom_navbar.Cluster;
import com.example.test_bottom_navbar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddClusterActivity extends AppCompatActivity {
    String Admin,i,district_name,C_lat,C_lng,SetDefault_Date;
    String clusterDate,clusterSubdistrict,clusterDistrict,cluster_news_patient,clusterLat,clusterLng;
    String place_def,subdistrict_def,district_def,news_patient_def,Clat_def,Clng_def;
    double Cluster_Lat,Cluster_Lng;
    int patientNum;
    private Spinner spinner_district,spinner_subdistrict;
    private TextView txtLat,txtLng;
    ArrayAdapter<String> dataAdapter;

    String[] district = {"เมืองเชียงใหม่","จอมทอง","เเม่เเจ่ม","เชียงดาว","ดอยสะเก็ด","แม่แตง","แม่ริม","สะเมิง","ฝาง","แม่อาย","พร้าว","สันป่าตอง","สันกำแพง","สันทราย","หางดง","ฮอด","ดอยเต่า","อมก๋อย","สารภี","เวียงแหง","ไชยปราการ","แม่วาง","แม่ออน","ดอยหล่อ"};
    String[] sub_CM = {"ศรีภูมิ","พระสิงห์","หายยา","ช้างม่อย","ช้างคลาน","วัดเกต","ช้างเผือก","สุเทพ","แม่เหียะ","ป่าแดด","หนองหอย","ท่าศาลา","หนองป่าครั่ง","ฟ้าฮ่าม","ป่าตัน","สันผีเสื้อ"};
    String[] sub_Chomthong = {"ข่วงเปา","สบเตี๊ยะ","บ้านแปะ","ดอยแก้ว","แม่สอย"};
    String[] sub_MaeJam = {"ท่าผา","บ้านทับ","แม่ศึก","แม่นาจร","บ้านจันทร์","ปางหินฝน","กองแขก","แม่แดด","แจ่มหลวง"};
    String[] sub_ChiangDao = {"เชียงดาว","เมืองนะ","เมืองงาย","แม่นะ","เมืองคอง","ปิงโค้ง","ทุ่งข้าวพวง"};
    String[] sub_DoiSaket= {"เชิงดอย","สันปูเลย","ลวงเหนือ","ป่าป้อง","สง่าบ้าน","ป่าลาน","ตลาดขวัญ","สำราญราษฎร์","แม่คือ","ตลาดใหญ่","แม่ฮ้อยเงิน","แม่โป่ง","ป่าเมี่ยง","เทพเสด็จ"};
    String[] sub_MaeTaeng = {"สันมหาพน","แม่แตง","ขี้เหล็ก","ช่อแล","แม่หอพระ","สบเปิง","บ้านเป้า","สันป่ายาง","ป่าแป๋","เมืองก๋าย","บ้านช้าง","กื้ดช้าง","อินทขิล","สมก๋าย"};
    String[] sub_MaeRim = {"ริมใต้","ริมเหนือ","สันโป่ง","ขี้เหล็ก","สะลวง","ห้วยทราย","แม่แรม","โป่งแยง","แม่สา","ดอนแก้ว","เหมืองแก้ว"};
    String[] sub_Samoeng = {"สะเมิงใต้","สะเมิงเหนือ","แม่สาบ","บ่อแก้ว","ยั้งเมิน"};
    String[] sub_Fang = {"เวียง","ม่อนปิ่น","แม่งอน","แม่สูน","สันทราย","แม่คะ","แม่ข่า","โป่งน้ำร้อน"};
    String[] sub_MaeEye = {"แม่อาย","แม่สาว","สันต้นหมื้อ","แม่นาวาง","ท่าตอน","บ้านหลวง","มะลิกา"};
    String[] sub_Phrao = {"เวียง","ทุ่งหลวง","ป่าตุ้ม","ป่าไหน่","สันทราย","บ้านโป่ง","น้ำแพร่","เขื่อนผาก","แม่แวน","แม่ปั๋ง","ลโหล่งขอด"};
    String[] sub_SanPaTong = {"ยุหว่า","สันกลาง","ท่าวังพร้าว","มะขามหลวง","แม่ก๊า","บ้านแม","บ้านกลาง","ทุ่งสะโตก","ทุ่งต้อม","น้ำบ่อหลวง","มะขุนหวาน"};
    String[] sub_Sankumpang = {"สันกำแพง","ทรายมูล","ร้องวัวแดง","บวกค้าง","แช่ช้าง","ออนใต้","แม่ปูคา","ห้วยทราย","ต้นเปา","สันกลาง"};
    String[] sub_SanSai = {"สันทรายหลวง","สันทรายน้อย","สันพระเนตร","สันนาเม็ง","สันป่าเปา","หนองแหย่ง","หนองจ๊อม","หนองหาร","แม่แฝก","แม่แฝกใหม่","เมืองเล็น","ป่าไผ่"};
    String[] sub_HangDong = {"หางดง","หนองแก๋ว","หารแก้ว","หนองตอง","ขุนคง","สบแม่ข่า","บ้านแหวน","สันผักหวาน","หนองควาย","บ้านปง","น้ำแพร่"};
    String[] sub_Hot = {"หางดง","ฮอด","บ้านตาล","บ่อหลวง","บ่อสลี","นาคอเรือ"};
    String[] sub_DoiTao = {"ดอยเต่า","ท่าเดื่อ","มืดกา","บ้านแอ่น","บงตัน","โปงทุ่ง"};
    String[] sub_AumKoi = {"อมก๋อย","ยางเปียง","แม่ตื่น","ม่อนจอง","สบโขง","นาเกียน"};
    String[] sub_Sarapee = {"ยางเนิ้ง","สารภี","ชมภู","ไชยสถาน","ขัวมุง","หนองแฝก","หนองผึ้ง","ท่ากว้าง","ดอนแก้ว","ท่าวังตาล","สันทราย","ป่าบง"};
    String[] sub_WiangHaeg = {"เมืองแหง","เปียงหลวง","แสนไห"};
    String[] sub_Chaiprakarn = {"ปงตำ","ศรีดงเย็น","แม่ทะลบ","หนองบัว"};
    String[] sub_MaeWang = {"บ้านกาด","ทุ่งปี๊","ทุ่งรวงทอง","แม่วิน","ดอนเปา"};
    String[] sub_MaeOn = {"ออนเหนือ","ออนกลาง","บ้านสหกรณ์","ห้วยแก้ว","แม่ทา","ทาเหนือ"};
    String[] sub_DoiLo = {"ดอยหล่อ","สองแคว","ยางคราม","สันติสุข"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cluster);

        Intent intentLatLng = getIntent();
        Cluster_Lat = intentLatLng.getDoubleExtra("Cluster_Lat",Cluster_Lat);
        Cluster_Lng = intentLatLng.getDoubleExtra("Cluster_Lng",Cluster_Lng);

        Intent intentget = getIntent();
        place_def = intentget.getStringExtra("place_def");
        subdistrict_def = intentget.getStringExtra("subdistrict_def");
        district_def = intentget.getStringExtra("district_def");
        news_patient_def = intentget.getStringExtra("news_patient_def");

        String String_place_def = place_def;
        EditText place = findViewById(R.id.txtadd_place);
        place.setText(String_place_def);

/*      String String_subdistrict_def = subdistrict_def;
        Spinner subdistrict = findViewById(R.id.txtadd_subdistrict);

        String String_district_def = district_def;
        Spinner district = findViewById(R.id.txtadd_district);*/

        String String_news_patient_def = news_patient_def;
        EditText news_patient = findViewById(R.id.txtadd_newpatient);
        news_patient.setText(String_news_patient_def);

        if (Cluster_Lat != 0.0 && Cluster_Lng != 0.0) {
            this.GetLatLngFromMap();
        }

        Intent intent = getIntent();
        Admin = intent.getStringExtra("Admin");

        DateClusterDefault();

        spinner_district = findViewById(R.id.txtadd_district);
        dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item,district);
        spinner_district.setAdapter(dataAdapter);
        spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(AddClusterActivity.this, "เลือก "+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                district_name = parent.getItemAtPosition(position).toString();
                if (district_name == "เมืองเชียงใหม่") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_CM);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "จอมทอง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Chomthong);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "เเม่เเจ่ม") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeJam);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "เชียงดาว") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_ChiangDao);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "ดอยสะเก็ด") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_DoiSaket);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "แม่แตง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeTaeng);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "แม่ริม") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeRim);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "สะเมิง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Samoeng);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "ฝาง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Fang);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "แม่อาย") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeEye);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "พร้าว") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Phrao);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "สันป่าตอง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_SanPaTong);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "สันกำแพง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Sankumpang);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "สันทราย") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_SanSai);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "หางดง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_HangDong);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "ฮอด") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Hot);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "ดอยเต่า") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_DoiTao);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "อมก๋อย") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_AumKoi);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "สารภี") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Sarapee);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "เวียงแหง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_WiangHaeg);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "ไชยปราการ") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Chaiprakarn);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "แม่วาง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeWang);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "แม่ออน") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeOn);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }else if (district_name == "ดอยหล่อ") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_DoiLo);
                    spinner_subdistrict.setAdapter(dataAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        /*final Spinner district = (Spinner) findViewById(R.id.txtadd_district);
        String clusterDistrict = district.getSelectedItem().toString();*/
        //for (int i=0;i < district.length;i++) {}
    }

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    public void ClickDateCluster(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddClusterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.txtadd_datecluster);
                String day = checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month + 1));
                txtdate.setText(day + "-" + months + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void DateClusterDefault(){
        Calendar calendar = Calendar.getInstance();
        EditText txtdate = findViewById(R.id.txtadd_datecluster);
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String day = checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth + 1));
        txtdate.setText(day + "-" + months + "-" + mYear);
        SetDefault_Date = txtdate.getText().toString();
        txtdate.setText(SetDefault_Date);
    }

    public void GetLatLngFromMap(){
        String C_lat,C_lng;
        C_lat = Double.toString(Cluster_Lat);
        C_lng = Double.toString(Cluster_Lng);
        TextView txtlat = findViewById(R.id.txtLat);
        txtlat.setText(C_lat);
        TextView txtlng = findViewById(R.id.txtLng);
        txtlng.setText(C_lng);
    }


    public void ClickBTNAddCluster(View view) {
        final EditText date = (EditText) findViewById(R.id.txtadd_datecluster);
        final EditText place = (EditText) findViewById(R.id.txtadd_place);
        final Spinner subdistrict = (Spinner) findViewById(R.id.txtadd_subdistrict);
        final Spinner district = (Spinner) findViewById(R.id.txtadd_district);
        final EditText newspatient = (EditText) findViewById(R.id.txtadd_newpatient);

        final EditText lat = (EditText) findViewById(R.id.txtLat);
        final EditText lng = (EditText) findViewById(R.id.txtLng);

        String clusterDate = date.getText().toString();
        String clusterPlace = place.getText().toString();
        String clusterSubdistrict = subdistrict.getSelectedItem().toString();
        String clusterDistrict = district.getSelectedItem().toString();
        String cluster_news_patient = newspatient.getText().toString();

        patientNum = Integer.parseInt(cluster_news_patient);

        String clusterLat = lat.getText().toString();
        String clusterLng = lng.getText().toString();

        if (clusterDate.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก วันที่", Toast.LENGTH_SHORT).show();

        } else if (clusterPlace.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก สถานที่", Toast.LENGTH_SHORT).show();

        } else if (clusterSubdistrict.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ตำบล", Toast.LENGTH_SHORT).show();

        } else if (clusterDistrict.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก อำเภอ", Toast.LENGTH_SHORT).show();

        } else if(cluster_news_patient.equals("")){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ยอดผู้ติดเชื้อใหม่", Toast.LENGTH_SHORT).show();

        } else if(clusterLat.equals("")){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ละติจูดของสถานที่", Toast.LENGTH_SHORT).show();

        } else if(clusterLng.equals("")){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ลองจิจูดของสถานที่", Toast.LENGTH_SHORT).show();

        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ti411app-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference("admin001/cluster/"+clusterDistrict);
            Query query1 = myRef.orderByKey().equalTo(clusterPlace);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String Error = "F";
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Error = "T";
                    }
                    if (Error.equals("F")) {
                        Cluster CT = new Cluster(clusterDate,clusterPlace,clusterSubdistrict,clusterDistrict,cluster_news_patient,clusterLat,clusterLng);
                        DatabaseReference stu1 = myRef.child(clusterPlace);
                        stu1.setValue(CT);
                        Intent intent = new Intent(AddClusterActivity.this, ListRiskAreaActivity.class);
                        intent.putExtra("clusterPlace", clusterPlace);
                        intent.putExtra("patient_number",patientNum);
                        Toast.makeText(AddClusterActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_LONG).show();
                        intent.putExtra("Admin", Admin);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddClusterActivity.this, "ชื่อสถานที่เกิดคลัสเตอร์ ซ้ำ", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    public void ClickBTNCancel (View view){
        Intent intent = new Intent(AddClusterActivity.this, Mainpage_admin.class);
        startActivity(intent);
    }

    public void ClickGetlatlng (View view){
        EditText place = findViewById(R.id.txtadd_place);
        Spinner subdistrict = findViewById(R.id.txtadd_subdistrict);
        Spinner district = findViewById(R.id.txtadd_district);
        EditText newspatient = findViewById(R.id.txtadd_newpatient);
        EditText lat = findViewById(R.id.txtLat);
        EditText lng = findViewById(R.id.txtLng);

        place_def = place.getText().toString();
        subdistrict_def = subdistrict.getSelectedItem().toString();
        district_def = district.getSelectedItem().toString();
        news_patient_def = newspatient.getText().toString();
        Clat_def = lat.getText().toString();
        Clng_def = lng.getText().toString();

        Intent intent = new Intent(AddClusterActivity.this, GetLatLngClusterActivity.class);
        intent.putExtra("place_def",place_def);
        intent.putExtra("subdistrict_def",subdistrict_def);
        intent.putExtra("district_def",district_def);
        intent.putExtra("news_patient_def",news_patient_def);
        intent.putExtra("Clat_def",Clat_def);
        intent.putExtra("Clng_def",Clng_def);

        startActivity(intent);
    }

}