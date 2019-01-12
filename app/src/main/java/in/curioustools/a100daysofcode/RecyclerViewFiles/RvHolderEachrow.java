package in.curioustools.a100daysofcode.RecyclerViewFiles;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.curioustools.a100daysofcode.R;

public class RvHolderEachrow extends RecyclerView.ViewHolder {
    private TextView quoteSymbol,quote,author;


    public RvHolderEachrow(@NonNull View itemView) {
        super(itemView);
        this.quoteSymbol =itemView.findViewById(R.id.text_eachrow_quotesymb);
        this.quote =itemView.findViewById(R.id.text_eachrow_quote);
        this.author =itemView.findViewById(R.id.text_eachrow_author);
    }

    public void bindData(RvMainData data){
        int x =(int)System.currentTimeMillis()%2;
        String symbol = itemView.getResources()
                .getString(x==0?R.string.quote_left:R.string.quote_right);

        this.quoteSymbol.setText(symbol);
        this.quote.setText(data.getQuote());
        this.author.setText(data.getAuthorName());

    }
}
