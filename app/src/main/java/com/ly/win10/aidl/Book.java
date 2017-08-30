package com.ly.win10.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/8/28.
 */

public class Book implements Parcelable{
    public int bookId;
    public String bookName;

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bookId);
        parcel.writeString(bookName);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        @Override
        public Book[] newArray(int i) {
            return new Book[0];
        }
    };

    private Book(Parcel in){
        bookId = in.readInt();
        bookName = in.readString();
    }

    @Override
    public String toString() {
        return "{id:"+bookId+",name:"+bookName+"}";
    }
}
