package com.fenixarts.nenektrivia.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.IntDef;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fenixarts.nenektrivia.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * QuinielaPicante
 * Created by terry0022 on 21/09/17 - 16:48.
 */

public class Notify extends Toast {

    private int duration;
    private Holder holder = new Holder();
    private CharSequence text;

    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;
    public static final int NOTIFY = 1;
    public static final int NOTIFY_INFO = 2;
    public static final int NOTIFY_SUCCESS = 3;
    public static final int NOTIFY_WARNING = 4;
    public static final int NOTIFY_ALERT = 5;

    /**
     * Duration
     */
    @IntDef({LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {}

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public Notify(Context context) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.notify,null);
        holder.message = (TextView)view.findViewById(R.id.message_toast);
        holder.layoutRootToast = (LinearLayout)view.findViewById(R.id.layout_root_toast);
        view.setTag(holder);
        this.setView(view);
    }

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application} or {@link Activity} object.
     * @param duration duration for notify
     *                 @see #LENGTH_SHORT
     *                 @see #LENGTH_LONG
     * @param message message in notify
     * @param type notify type
     */
    @SuppressLint("InflateParams")
    public Notify(Context context, @Duration int duration, CharSequence message, Integer type) {
        super(context);
        this.setDuration(duration);

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Holder notifyHolder = new Holder();
        View view;

        assert layoutInflater != null;
        view = layoutInflater.inflate(R.layout.notify,null);
        notifyHolder.message = (TextView)view.findViewById(R.id.message_toast);
        notifyHolder.message.setText(message);
        notifyHolder.layoutRootToast = (LinearLayout)view.findViewById(R.id.layout_root_toast);
        view.setTag(notifyHolder);

        this.setView(view);
        switch (type){
            case NOTIFY:
                notifyHolder.layoutRootToast.setBackgroundColor(ContextCompat.getColor(context, R.color.colorNormal));
                break;
            case NOTIFY_INFO:
                notifyHolder.layoutRootToast.setBackgroundColor(ContextCompat.getColor(context, R.color.colorInfo));
                notifyHolder.message.setTextColor(Color.BLACK);
                break;
            case NOTIFY_SUCCESS:
                notifyHolder.layoutRootToast.setBackgroundColor(ContextCompat.getColor(context, R.color.colorSuccess));
                break;
            case NOTIFY_WARNING:
                notifyHolder.layoutRootToast.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWarning));
                break;
            case NOTIFY_ALERT:
                notifyHolder.layoutRootToast.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAlert));
                break;
            default:
        }

        super.show();
    }

    public void setNotifyDuration(@Duration int duration){
        this.duration = duration;
        this.setDuration(duration);
    }

    public int getNotifyDuration(){
        return duration;
    }

    /**
     * colocar gravedad
     * @param gravity int Gravity gravedad
     * @param xOffset int eje X
     * @param yOffset int eje Y
     */
    public void setNotifyGravity(int gravity, int xOffset, int yOffset){
        this.setGravity(gravity,xOffset,yOffset);
    }

    public int getNotifyGravity(){
        return this.getGravity();
    }

    public int getNotifyXOffset(){
        return this.getXOffset();
    }

    public int getNotifyYOffset(){
        return this.getYOffset();
    }

    public void setNotifyText(CharSequence text){
        this.text = text;
        holder.message.setText(text);
    }

    public CharSequence getNotifyText(){
        return text;
    }

    public void setNotifyBackground(int color){
        holder.layoutRootToast.setBackgroundColor(color);
    }

    /**
     * mostrar notify
     */
    public void showNotify(){
        super.show();
    }

    /**
     * hacer una notificacion
     * @param context Context contexto
     * @param text CharSequence texto a mostrar
     * @param duration int duracion
     * @return GdevNotify notificacion
     */
    public static Notify makeNotify(Context context, CharSequence text, @Duration int duration){
        Notify notify = new Notify(context);
        notify.setNotifyDuration(duration);
        notify.setNotifyText(text);
        notify.setNotifyBackground(R.color.colorNormal);
        return notify;
    }

    static class Holder{
        LinearLayout layoutRootToast;
        TextView message;
    }
}
