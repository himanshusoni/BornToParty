package com.hexade.borntoparty.main.dummy;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyBirthday {

    private static String[][] birthdayData=

            new String[][]{
                    {
                            "Astrid", "Mrs. Rozella O'Reilly", "8/15/1997", "0"
                    },
                    {
                            "Sophia_Bahringer", "Aidan Bauch III", "5/19/2015", "1"
                    },
                    {
                            "Kolby_Grant", "Aidan Tillman", "10/5/1996", "2"
                    },
                    {
                            "Vida_Bahringer", "Joany McCullough", "1/9/2010", "3"
                    },
                    {
                            "Rollin", "Everette Beahan", "9/8/1998", "4"
                    },
                    {
                            "Leo", "Kaci Douglas", "10/27/2013", "5"
                    },
                    {
                            "Demond", "Nolan,Araceli Russel", "12/20/1998", "6"
                    },
                    {
                            "Milton_Raynor", "Luther Wilderman", "3/1/1992", "7"
                    },
                    {
                            "Bethel", "Marjory Hills", "5/5/1991", "8"
                    },
                    {
                            "Marcelino", "Autumn Tromp", "9/16/1985", "9"
                    }
            };

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = birthdayData.length;

    static {
        Log.i("CRASH", "HERE");
        // Add some sample items.
        for (int i = 0; i < COUNT; i++) {
            addItem(createDummyItem(birthdayData[i], i));
        }

        Collections.sort(ITEMS, new Comparator<DummyItem>() {
            public int compare(DummyItem arg0, DummyItem arg1) {
                Calendar c1 = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();

                c1.setTime(arg1.birthday);
                c2.setTime(new Date());

                int dayDiff = c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
                int monthDiff = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
                // Compare in reverse order ie biggest first
                return arg1.birthday.compareTo(new Date());
            }
        });
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(String[] data, int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position), data);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public final String username;
        public final String name;
        public final Date birthday;



        public DummyItem(String id, String content, String details, String[] data) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.username = data[0];
            this.name = data[1];
            this.birthday = new Date(data[2]);
        }

        public String getFormattedDate(){
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.birthday);
            return new SimpleDateFormat("MMMM d").format(cal.getTime());
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
