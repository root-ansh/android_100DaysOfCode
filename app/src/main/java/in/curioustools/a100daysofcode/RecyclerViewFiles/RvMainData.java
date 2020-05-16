package in.curioustools.a100daysofcode.RecyclerViewFiles;

import android.support.annotation.NonNull;

import java.text.MessageFormat;

public class RvMainData {
    private String quote,authorName;

    public RvMainData(String quote, String authorName) {
        this.quote = quote;
        this.authorName = authorName;
    }

    public String getQuote() {
        return quote;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @NonNull @Override
    public String toString() {
        return MessageFormat.format(
                "RvMainData{quote='{0}',authorName='{1}'}", quote, authorName);
    }
}
