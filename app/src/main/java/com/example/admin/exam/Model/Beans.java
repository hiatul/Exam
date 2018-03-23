package com.example.admin.exam.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 24-Jan-17.
 */

public class Beans {
    private String Name,Email,Phone,Profile,Product,Key,Sol;
    private int Marks,QNo;
    private ArrayList Data;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private String Question,A,B,C,D,ANS,PID,QID;
    private ArrayList ProductNameList;
    int TimeOut,checked;

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(int timeOut) {
        TimeOut = timeOut;
    }

    public String getQID() {
        return QID;
    }

    public void setQID(String QID) {
        this.QID = QID;
    }

    public int getQNo() {
        return QNo;
    }

    public void setQNo(int QNo) {
        this.QNo = QNo;
    }

    public int getMarks() {
        return Marks;
    }

    public void setMarks(int marks) {
        Marks = marks;
    }

    public String getSol() {
        return Sol;
    }

    public void setSol(String sol) {
        Sol = sol;
    }

    public ArrayList getProductKeyList() {
        return ProductKeyList;
    }

    public void setProductKeyList(ArrayList productKeyList) {
        ProductKeyList = productKeyList;
    }

    private ArrayList ProductIDList,ProductKeyList;


    public ArrayList getProductNameList() {
        return ProductNameList;
    }

    public void setProductNameList(ArrayList productNameList) {
        ProductNameList = productNameList;
    }

    public ArrayList getProductIDList() {
        return ProductIDList;
    }

    public void setProductIDList(ArrayList productIDList) {
        ProductIDList = productIDList;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getANS() {
        return ANS;
    }

    public void setANS(String ANS) {
        this.ANS = ANS;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }

    public ArrayList getData() {
        return Data;
    }

    public void setData(ArrayList data) {
        Data = data;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
