package jhonny.marvelcomics.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import jhonny.marvelcomics.utils.FontCache;

public class TradeTextView extends TextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public TradeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public TradeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        switch (textStyle) {
            case Typeface.BOLD: // bold
                return FontCache.getTypeface("font/TradeGothic_BOLD.otf", context);
            case Typeface.ITALIC: // italic
                return FontCache.getTypeface("font/TradeGothic_ITALIC.otf", context);
            case Typeface.NORMAL: // regular
            default:
                return FontCache.getTypeface("font/TradeGothic_NORMAL.otf", context);
        }
    }
}