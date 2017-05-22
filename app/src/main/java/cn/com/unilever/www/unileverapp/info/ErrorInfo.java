package cn.com.unilever.www.unileverapp.info;

import android.os.Parcel;
import android.os.Parcelable;

public class ErrorInfo implements Parcelable {
    public String thumbnail_pic_s;//图片网址
    public String title;//标题
    public String date;//日期
    public String category;//类别
    public String author_name; //作者
    public String url;//新闻内容

    public static final Creator<ErrorInfo> CREATOR = new Creator<ErrorInfo>() {
        @Override
        public ErrorInfo createFromParcel(Parcel in) {
            ErrorInfo info = new ErrorInfo();
            info.date = in.readString();
            info.title = in.readString();
            info.thumbnail_pic_s = in.readString();
            info.category = in.readString();
            info.author_name = in.readString();
            info.url = in.readString();
            return info;
        }

        @Override
        public ErrorInfo[] newArray(int size) {
            return new ErrorInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnail_pic_s);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(category);
        dest.writeString(author_name);
        dest.writeString(url);
    }
}
