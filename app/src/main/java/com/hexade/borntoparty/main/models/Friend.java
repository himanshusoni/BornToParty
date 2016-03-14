package com.hexade.borntoparty.main.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by himanshusoni on 3/13/16.
 */
public class Friend {
    public String gender;
    public Name name;
//    public String location;
    public String email;
    public String username;
    public String password;
    public Long dob;
    public String phone;
    public String cell;
    public Picture picture;

    public int daysLeft;
    public int age;
    public Date dateOfBirth;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDob() {
        return dob;
    }

    public void setDob(Long da) {
        Date dob = new Date(da * 1000);

        Calendar d = Calendar.getInstance();
        d.setTime(dob);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - d.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= d.get(Calendar.DAY_OF_YEAR))
            age--;

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        d.set(Calendar.YEAR, year);

        if(d.before(cal))
            d.set(Calendar.YEAR, year + 1);

        // weird calculation. (Calendar.getTime()) gives Date. Date.getTime(); give long.
        long days = d.getTime().getTime() - cal.getTime().getTime();

        this.age = age;
        this.daysLeft = (int) TimeUnit.DAYS.convert(days, TimeUnit.MILLISECONDS);

        this.dateOfBirth = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public int getAge() {
        return age;
    }

    public String getFormattedDob(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOfBirth);
        return new SimpleDateFormat("MMMM d").format(cal.getTime());
    }

    public Date getDateOfBirth(){
        return dateOfBirth;
    }

    public class Name{
        private String first;
        private String last;
        private String title;

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFullName(){
            return first + " " + last;
        }
    }

    public class Picture {
        public String large;
        public String medium;
        public String thumbnail;

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
