package hg.crx.findpoint.widget;

/**
 * Created by Sawatari on 2016/11/16.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import hg.crx.findpoint.R;

/**
 * Copyright 2015 florent37, Inc.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * https://github.com/florent37/MaterialTextField
 */

public class MaterialTextField extends FrameLayout {

    protected View card;
    protected ImageView image;
    protected EditText editText;
    protected ViewGroup editTextLayout;

    protected boolean expanded = false;

    protected int ANIMATION_DURATION = -1;
    protected boolean OPEN_KEYBOARD_ON_FOCUS = true;
    protected int labelColor = -1;
    protected int cardColor = -1;
    protected int imageDrawableId = -1;
    protected int cardCollapsedHeight = -1;

    protected void handleAttributes(Context context, AttributeSet attrs) {
        try {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.MaterialTextField);

            {
                ANIMATION_DURATION = styledAttrs.getInteger(R.styleable.MaterialTextField_mtf_animationDuration, 400);
            }
            {
                OPEN_KEYBOARD_ON_FOCUS = styledAttrs.getBoolean(R.styleable.MaterialTextField_mtf_openKeyboardOnFocus, false);
            }
            {
                labelColor = styledAttrs.getColor(R.styleable.MaterialTextField_mtf_labelColor, -1);
            }
            {
                cardColor = styledAttrs.getColor(R.styleable.MaterialTextField_mtf_cardColor, -1);
            }
            {
                imageDrawableId = styledAttrs.getResourceId(R.styleable.MaterialTextField_mtf_image, -1);
            }
            {
                cardCollapsedHeight = styledAttrs.getDimensionPixelOffset(R.styleable.MaterialTextField_mtf_cardCollapsedHeight, context.getResources().getDimensionPixelOffset(R.dimen.mtf_cardHeight_initial));
            }

            cardCollapsedHeight += context.getResources().getDimensionPixelOffset(R.dimen.mtf_cardview_additionnal);

            styledAttrs.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MaterialTextField(Context context) {
        super(context);
    }

    public MaterialTextField(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleAttributes(context, attrs);
    }

    public MaterialTextField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleAttributes(context, attrs);
    }


    protected EditText findEditTextChild() {
        if (getChildCount() > 0 && getChildAt(0) instanceof EditText) {
            return (EditText) getChildAt(0);
        }
        return null;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        editText = findEditTextChild();
        if (editText == null)
            return;

        addView(LayoutInflater.from(getContext()).inflate(R.layout.mtf_layout, this, false));

        editText.setSingleLine();
        editTextLayout = (ViewGroup) findViewById(R.id.mtf_editTextLayout);
        removeView(editText);
        editTextLayout.addView(editText);

        card = findViewById(R.id.mtf_card);
        card.getLayoutParams().height = cardCollapsedHeight;
        card.requestLayout();

        image = (ImageView) findViewById(R.id.mtf_image);
        ViewHelper.setAlpha((View) image, 0);
        ViewHelper.setScaleX(image, 0.4f);
        ViewHelper.setScaleY(image, 0.4f);

        ViewHelper.setAlpha(editText, 0f);
        editText.setBackgroundColor(Color.TRANSPARENT);

        customizeFromAttributes();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

    }

    protected void customizeFromAttributes() {
        if (cardColor != -1) {
            if (card instanceof CardView)
                CardView.class.cast(this.card).setCardBackgroundColor(cardColor);
        }
        if (imageDrawableId != -1) {
            this.image.setImageDrawable(getContext().getResources().getDrawable(imageDrawableId));
        }
    }

    public void toggle() {
        if (expanded)
            reduce();
        else
            expand();
    }

    public void reduce() {
        if (expanded) {

            ValueAnimator expand = ValueAnimator.ofInt(card.getHeight(), cardCollapsedHeight);
            expand.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer value = (Integer) animation.getAnimatedValue();
                    card.getLayoutParams().height = value.intValue();
                    card.requestLayout();
                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(ANIMATION_DURATION);
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(image, "alpha", 0),
                    ObjectAnimator.ofFloat(image, "scaleX", 0.4f),
                    ObjectAnimator.ofFloat(image, "scaleY", 0.4f),

                    ObjectAnimator.ofFloat(editText, "alpha", 0),
                    expand
            );
            animatorSet.start();

            if (OPEN_KEYBOARD_ON_FOCUS)
                ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            editText.clearFocus();

            expanded = false;
        }
    }

    public void expand() {
        if (!expanded) {

            ValueAnimator expand = ValueAnimator.ofInt(0, getContext().getResources().getDimensionPixelOffset(R.dimen.mtf_cardHeight_final));
            expand.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer value = (Integer) animation.getAnimatedValue();
                    card.getLayoutParams().height = value.intValue();

                    card.requestLayout();
                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(ANIMATION_DURATION);
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(image, "alpha", 1),
                    ObjectAnimator.ofFloat(image, "scaleX", 1),
                    ObjectAnimator.ofFloat(image, "scaleY", 1),

                    ObjectAnimator.ofFloat(editText, "alpha", 1),
                    expand
            );
            animatorSet.start();

            editText.requestFocus();
            if (OPEN_KEYBOARD_ON_FOCUS)
                ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

            expanded = true;
        }
    }

    public View getCard() {
        return card;
    }

    public ImageView getImage() {
        return image;
    }

    public EditText getEditText() {
        return editText;
    }

    public ViewGroup getEditTextLayout() {
        return editTextLayout;
    }

    public boolean isExpanded() {
        return expanded;
    }

}
