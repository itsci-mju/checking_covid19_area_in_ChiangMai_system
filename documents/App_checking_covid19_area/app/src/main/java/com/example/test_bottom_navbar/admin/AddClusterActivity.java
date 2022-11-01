package com.example.test_bottom_navbar.admin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test_bottom_navbar.Cluster;
import com.example.test_bottom_navbar.Cluster_report;
import com.example.test_bottom_navbar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class AddClusterActivity extends AppCompatActivity {
    String Admin,i,district_name,subdistrict_name,C_lat,C_lng,SetDefault_Date,SetDefault_DateCount10day;
    String clusterDate,clusterSubdistrict,clusterDistrict,cluster_news_patient,clusterLat,clusterLng;
    String place_def,news_patient_def,Clat_def,Clng_def;
    String subdistrict_def,district_def;
    double Cluster_Lat,Cluster_Lng;
    int patientNum,dt,sdt,numAllpatient, numnewpatinet_today,numgetwellpatinet_today,numAllpatient_district,numAllgetwell_district,numAllhealing_district;
    private Spinner spinner_district,spinner_subdistrict;
    private TextView txtLat,txtLng;
    ArrayAdapter<String> dataAdapter_district;
    ArrayAdapter<String> dataAdapter_subdistrict;
    private static final Pattern Check_special = Pattern.compile("[ก-์]$");
    private static final Pattern Check_thiafont = Pattern.compile("[ก-์]$");
    private static final Pattern Check_Engfont = Pattern.compile("[A-Za-z]$");
    private static final Pattern Check_dot = Pattern.compile("[_.-]$");
    //private static final Pattern Check_number = Pattern.compile("^[0-9]{1,}$");

    String[] district = {"เมืองเชียงใหม่","แม่ริม","สันทราย","สารภี"};
    //String[] district = {"เมืองเชียงใหม่","จอมทอง","เเม่เเจ่ม","เชียงดาว","ดอยสะเก็ด","แม่แตง","แม่ริม","สะเมิง","ฝาง","แม่อาย","พร้าว","สันป่าตอง","สันกำแพง","สันทราย","หางดง","ฮอด","ดอยเต่า","อมก๋อย","สารภี","เวียงแหง","ไชยปราการ","แม่วาง","แม่ออน","ดอยหล่อ"};
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
    String Letter10day;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cluster);

        Intent intentLatLng = getIntent();
        Cluster_Lat = intentLatLng.getDoubleExtra("Cluster_Lat",Cluster_Lat);
        Cluster_Lng = intentLatLng.getDoubleExtra("Cluster_Lng",Cluster_Lng);

        Intent intentget = getIntent();
        place_def = intentget.getStringExtra("place_def");

        dt = intentget.getIntExtra("dt",dt);
        Log.e("////////////////////////////////////////A", String.valueOf(dt));

        sdt = intentget.getIntExtra("sdt",sdt);
        Log.e("////////////////////////////////////////A", String.valueOf(sdt));

        news_patient_def = intentget.getStringExtra("news_patient_def");

        String String_place_def = place_def;
        EditText place = findViewById(R.id.txtadd_place);
        place.setText(String_place_def);

        String String_news_patient_def = news_patient_def;
        EditText news_patient = findViewById(R.id.txtadd_newpatient);
        news_patient.setText(String_news_patient_def);

        spinner_district = findViewById(R.id.txtadd_district);
        spinner_district.setSelection(dt);
        spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
        spinner_subdistrict.setSelection(sdt);

        if (Cluster_Lat != 0.0 && Cluster_Lng != 0.0) {
                this.GetLatLngFromMap();
        }

        Intent intent = getIntent();
        Admin = intent.getStringExtra("Admin");

        DateClusterDefault();

        dataAdapter_district = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item,district);
        spinner_district.setAdapter(dataAdapter_district);
        spinner_district.setSelection(dt);
        spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                district_name = parent.getItemAtPosition(position).toString();
                dt = spinner_district.getSelectedItemPosition();
                Log.e("dt = ", String.valueOf(dt));
                if (district_name == "เมืองเชียงใหม่") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_CM);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                    System.out.println("-------------------------------------------subdistrict----"+spinner_subdistrict.getSelectedItem().toString());
                }else if (district_name == "จอมทอง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Chomthong);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                    System.out.println("-------------------------------------------subdistrict----"+spinner_subdistrict.getSelectedItem().toString());
                }else if (district_name == "เเม่เเจ่ม") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeJam);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                    System.out.println("-------------------------------------------subdistrict----"+spinner_subdistrict.getSelectedItem().toString());
                }else if (district_name == "เชียงดาว") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_ChiangDao);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "ดอยสะเก็ด") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_DoiSaket);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "แม่แตง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeTaeng);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "แม่ริม") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeRim);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "สะเมิง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Samoeng);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "ฝาง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Fang);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "แม่อาย") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeEye);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "พร้าว") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Phrao);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "สันป่าตอง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_SanPaTong);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "สันกำแพง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Sankumpang);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "สันทราย") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_SanSai);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "หางดง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_HangDong);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "ฮอด") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Hot);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "ดอยเต่า") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_DoiTao);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "อมก๋อย") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_AumKoi);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "สารภี") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Sarapee);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "เวียงแหง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_WiangHaeg);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "ไชยปราการ") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_Chaiprakarn);
                    sdt = spinner_subdistrict.getSelectedItemPosition();
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "แม่วาง") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeWang);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "แม่ออน") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_MaeOn);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }else if (district_name == "ดอยหล่อ") {
                    spinner_subdistrict = findViewById(R.id.txtadd_subdistrict);
                    dataAdapter_subdistrict = new ArrayAdapter<String>(AddClusterActivity.this, android.R.layout.simple_spinner_dropdown_item, sub_DoiLo);
                    spinner_subdistrict.setAdapter(dataAdapter_subdistrict);
                    spinner_subdistrict.setSelection(sdt);
                    Log.e("sdt = ", String.valueOf(sdt));
                    spinner_subdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            sdt = spinner_subdistrict.getSelectedItemPosition();
                            Log.e("sdt = ", String.valueOf(sdt));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    public String checklength(String s) {
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    @SuppressLint("NewApi")
    public void ClickDateCluster(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddClusterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                TextView txtdate = findViewById(R.id.txtadd_datecluster);
                String day = checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month + 1));
                txtdate.setText(day + "-" + months + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    @SuppressLint("LongLogTag")
    public void DateClusterDefault(){
        Calendar calendar = Calendar.getInstance();
        TextView txtdate = findViewById(R.id.txtadd_datecluster);
        EditText txtnewpatient = findViewById(R.id.txtadd_newpatient);
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String day = checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth + 1));
        txtdate.setText(day + "-" + months + "-" + mYear);
        txtnewpatient.setText("0");

        Calendar calendar1 = Calendar.getInstance();
        int mYear1 = calendar1.get(Calendar.YEAR);
        int mMonth1 = calendar1.get(Calendar.MONTH);
        int mDay1 = calendar1.get(Calendar.DAY_OF_MONTH);
        String day1 = checklength(String.valueOf(mDay1));
        String months1 = checklength(String.valueOf(mMonth1 + 1));
        txtdate.setText(day1 + "-" + months1 + "-" + mYear1);

        int days = 31;
        if(months == "4" || months == "6" || months == "9" || months == "11"){
            days = 30 ;
        }
        ////////*Set Date latter 10 day*/////////
        SimpleDateFormat formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formattedDate = new SimpleDateFormat("dd-MM-yyyy");
        }
        Calendar ca_10 = Calendar.getInstance();
        ca_10.add(Calendar.DATE,10);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Letter10day = (String) (formattedDate.format(ca_10.getTime()));
        }
        System.out.println("Date Count 10 day leter :----------------------- "+Letter10day);
        ////////*End Set Date latter 10 day*/////////
        
        SetDefault_Date = txtdate.getText().toString();
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
        final TextView date = (TextView) findViewById(R.id.txtadd_datecluster);
        final EditText place = (EditText) findViewById(R.id.txtadd_place);
        final Spinner subdistrict = (Spinner) findViewById(R.id.txtadd_subdistrict);
        final Spinner district = (Spinner) findViewById(R.id.txtadd_district);
        final EditText newpatient = (EditText) findViewById(R.id.txtadd_newpatient);
        final EditText lat = (EditText) findViewById(R.id.txtLat);
        final EditText lng = (EditText) findViewById(R.id.txtLng);

        String clusterDate = date.getText().toString();
        String clusterPlace = place.getText().toString();
        String clusterSubdistrict = subdistrict.getSelectedItem().toString();
        String clusterDistrict = district.getSelectedItem().toString();

        String cluster_All_patient = newpatient.getText().toString();
        String cluster_getwell_patient = "0";
        String cluster_news_patient= cluster_All_patient;

        String cluster_newpatinet_today = "0";
        String cluster_getwellpatinet_today="0";
        String cluster_Allpatient_district="0";
        String cluster_Allgetwell_district="0";
        String cluster_Allhealing_district="0";
        String clusterDateEnd = Letter10day;

        if(!cluster_news_patient.equals("")){
            numAllpatient = Integer.parseInt(cluster_news_patient);
        }else{
            numAllpatient = 0;
        }
        numnewpatinet_today = Integer.parseInt(cluster_newpatinet_today);
        numgetwellpatinet_today = Integer.parseInt(cluster_getwellpatinet_today);
        numAllpatient_district = Integer.parseInt(cluster_Allpatient_district);
        numAllgetwell_district = Integer.parseInt(cluster_Allgetwell_district);
        numAllhealing_district = Integer.parseInt(cluster_Allhealing_district);

        numnewpatinet_today = numnewpatinet_today+numAllpatient;
        numAllpatient_district = numAllpatient_district+numAllpatient;
        numAllhealing_district = numAllhealing_district+numAllpatient;

        cluster_newpatinet_today = String.valueOf(numnewpatinet_today);
        cluster_Allpatient_district = String.valueOf(numAllpatient_district);
        cluster_Allhealing_district = String.valueOf(numAllhealing_district);

        String clusterLat = lat.getText().toString();
        String clusterLng = lng.getText().toString();

        /*if(!clusterPlace.matches(String.valueOf(Check_thiafont))) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอกข้อมูลให้ถูกต้อง", Toast.LENGTH_SHORT).show();

        }else*/ if (clusterDate.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอกวันที่", Toast.LENGTH_SHORT).show();

        } else if (clusterPlace.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก สถานที่", Toast.LENGTH_SHORT).show();

        } else if (clusterSubdistrict.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ตำบล", Toast.LENGTH_SHORT).show();

        } else if (clusterDistrict.equals("")) {
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก อำเภอ", Toast.LENGTH_SHORT).show();

        } else if(cluster_All_patient.equals("")){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ยอดผู้ติดเชื้อใหม่", Toast.LENGTH_SHORT).show();

        } else if(clusterLat.equals("")){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ละติจูดของสถานที่", Toast.LENGTH_SHORT).show();

        } else if(clusterLng.equals("")){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ลองจิจูดของสถานที่", Toast.LENGTH_SHORT).show();

        }else if(cluster_All_patient.equals("0")){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ยอดผู้ติดเชื้อ", Toast.LENGTH_SHORT).show();
        }else if(cluster_All_patient.equals("")){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ยอดผู้ติดเชื้อ", Toast.LENGTH_SHORT).show();
        /*else if(!Check_number.matcher(cluster_All_patient).matches()){
            Toast.makeText(AddClusterActivity.this, "กรุณากรอก ยอดผู้ป่วยเป็นตัวเลข", Toast.LENGTH_SHORT).show();*/
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
                        Cluster CT = new Cluster(clusterDate,clusterDateEnd,clusterPlace,clusterSubdistrict,clusterDistrict,cluster_getwell_patient,cluster_news_patient,cluster_All_patient,clusterLat,clusterLng);
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

            DatabaseReference myRef1 = database.getReference("admin001/history_cluster/"+clusterDate);
            Query query2 = myRef.orderByKey().equalTo(clusterDistrict);
            String finalCluster_newpatinet_today = cluster_newpatinet_today;
            String finalCluster_Allpatient_district = cluster_Allpatient_district;
            String finalCluster_Allhealing_district = cluster_Allhealing_district;
            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Cluster_report ctr = new Cluster_report(finalCluster_newpatinet_today,cluster_getwellpatinet_today,
                            finalCluster_Allpatient_district,cluster_Allgetwell_district, finalCluster_Allhealing_district);
                    DatabaseReference stu1 = myRef1.child(clusterDistrict);
                    stu1.setValue(ctr);
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




    @SuppressLint("LongLogTag")
    public void ClickGetlatlng (View view){
        EditText place = findViewById(R.id.txtadd_place);

        EditText newspatient = findViewById(R.id.txtadd_newpatient);
        EditText lat = findViewById(R.id.txtLat);
        EditText lng = findViewById(R.id.txtLng);

        place_def = place.getText().toString();
        Clat_def = lat.getText().toString();
        Clng_def = lng.getText().toString();

        Intent intent = new Intent(AddClusterActivity.this, GetLatLngClusterActivity.class);
        intent.putExtra("place_def",place_def);
        intent.putExtra("dt",dt);
        intent.putExtra("sdt",sdt);
        intent.putExtra("news_patient_def",news_patient_def);
        intent.putExtra("Clat_def",Clat_def);
        intent.putExtra("Clng_def",Clng_def);

        startActivity(intent);
    }

}