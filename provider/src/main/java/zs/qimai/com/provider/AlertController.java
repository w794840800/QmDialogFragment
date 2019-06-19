package zs.qimai.com.provider;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.app.AlertController;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public static class AlertParams {
    public final Context mContext;
    public final LayoutInflater mInflater;
    public int mIconId = 0;
    public Drawable mIcon;
    public int mIconAttrId = 0;
    public CharSequence mTitle;
    public View mCustomTitleView;
    public CharSequence mMessage;
    public CharSequence mPositiveButtonText;
    public Drawable mPositiveButtonIcon;
    public android.content.DialogInterface.OnClickListener mPositiveButtonListener;
    public CharSequence mNegativeButtonText;
    public Drawable mNegativeButtonIcon;
    public android.content.DialogInterface.OnClickListener mNegativeButtonListener;
    public CharSequence mNeutralButtonText;
    public Drawable mNeutralButtonIcon;
    public android.content.DialogInterface.OnClickListener mNeutralButtonListener;
    public boolean mCancelable;
    public DialogInterface.OnCancelListener mOnCancelListener;
    public DialogInterface.OnDismissListener mOnDismissListener;
    public DialogInterface.OnKeyListener mOnKeyListener;
    public CharSequence[] mItems;
    public ListAdapter mAdapter;
    public android.content.DialogInterface.OnClickListener mOnClickListener;
    public int mViewLayoutResId;
    public View mView;
    public int mViewSpacingLeft;
    public int mViewSpacingTop;
    public int mViewSpacingRight;
    public int mViewSpacingBottom;
    public boolean mViewSpacingSpecified = false;
    public boolean[] mCheckedItems;
    public boolean mIsMultiChoice;
    public boolean mIsSingleChoice;
    public int mCheckedItem = -1;
    public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
    public Cursor mCursor;
    public String mLabelColumn;
    public String mIsCheckedColumn;
    public boolean mForceInverseBackground;
    public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    public AlertController.AlertParams.OnPrepareListViewListener mOnPrepareListViewListener;
    public boolean mRecycleOnMeasure = true;

    public AlertParams(Context context) {
        this.mContext = context;
        this.mCancelable = true;
        this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    public void apply(AlertController dialog) {
        if (this.mCustomTitleView != null) {
            dialog.setCustomTitle(this.mCustomTitleView);
        } else {
            if (this.mTitle != null) {
                dialog.setTitle(this.mTitle);
            }

            if (this.mIcon != null) {
                dialog.setIcon(this.mIcon);
            }

            if (this.mIconId != 0) {
                dialog.setIcon(this.mIconId);
            }

            if (this.mIconAttrId != 0) {
                dialog.setIcon(dialog.getIconAttributeResId(this.mIconAttrId));
            }
        }

        if (this.mMessage != null) {
            dialog.setMessage(this.mMessage);
        }

        if (this.mPositiveButtonText != null || this.mPositiveButtonIcon != null) {
            dialog.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, (Message)null, this.mPositiveButtonIcon);
        }

        if (this.mNegativeButtonText != null || this.mNegativeButtonIcon != null) {
            dialog.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, (Message)null, this.mNegativeButtonIcon);
        }

        if (this.mNeutralButtonText != null || this.mNeutralButtonIcon != null) {
            dialog.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, (Message)null, this.mNeutralButtonIcon);
        }

        if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
            this.createListView(dialog);
        }

        if (this.mView != null) {
            if (this.mViewSpacingSpecified) {
                dialog.setView(this.mView, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
            } else {
                dialog.setView(this.mView);
            }
        } else if (this.mViewLayoutResId != 0) {
            dialog.setView(this.mViewLayoutResId);
        }

    }

    private void createListView(final AlertController dialog) {
        final AlertController.RecycleListView listView = (AlertController.RecycleListView)this.mInflater.inflate(dialog.mListLayout, (ViewGroup)null);
        Object adapter;
        if (this.mIsMultiChoice) {
            if (this.mCursor == null) {
                adapter = new ArrayAdapter<CharSequence>(this.mContext, dialog.mMultiChoiceItemLayout, 16908308, this.mItems) {
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        if (AlertController.AlertParams.this.mCheckedItems != null) {
                            boolean isItemChecked = AlertController.AlertParams.this.mCheckedItems[position];
                            if (isItemChecked) {
                                listView.setItemChecked(position, true);
                            }
                        }

                        return view;
                    }
                };
            } else {
                adapter = new CursorAdapter(this.mContext, this.mCursor, false) {
                    private final int mLabelIndex;
                    private final int mIsCheckedIndex;

                    {
                        Cursor cursor = this.getCursor();
                        this.mLabelIndex = cursor.getColumnIndexOrThrow(AlertController.AlertParams.this.mLabelColumn);
                        this.mIsCheckedIndex = cursor.getColumnIndexOrThrow(AlertController.AlertParams.this.mIsCheckedColumn);
                    }

                    public void bindView(View view, Context context, Cursor cursor) {
                        CheckedTextView text = (CheckedTextView)view.findViewById(16908308);
                        text.setText(cursor.getString(this.mLabelIndex));
                        listView.setItemChecked(cursor.getPosition(), cursor.getInt(this.mIsCheckedIndex) == 1);
                    }

                    public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        return AlertController.AlertParams.this.mInflater.inflate(dialog.mMultiChoiceItemLayout, parent, false);
                    }
                };
            }
        } else {
            int layout;
            if (this.mIsSingleChoice) {
                layout = dialog.mSingleChoiceItemLayout;
            } else {
                layout = dialog.mListItemLayout;
            }

            if (this.mCursor != null) {
                adapter = new SimpleCursorAdapter(this.mContext, layout, this.mCursor, new String[]{this.mLabelColumn}, new int[]{16908308});
            } else if (this.mAdapter != null) {
                adapter = this.mAdapter;
            } else {
                adapter = new AlertController.CheckedItemAdapter(this.mContext, layout, 16908308, this.mItems);
            }
        }

        if (this.mOnPrepareListViewListener != null) {
            this.mOnPrepareListViewListener.onPrepareListView(listView);
        }

        dialog.mAdapter = (ListAdapter)adapter;
        dialog.mCheckedItem = this.mCheckedItem;
        if (this.mOnClickListener != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    AlertController.AlertParams.this.mOnClickListener.onClick(dialog.mDialog, position);
                    if (!AlertController.AlertParams.this.mIsSingleChoice) {
                        dialog.mDialog.dismiss();
                    }

                }
            });
        } else if (this.mOnCheckboxClickListener != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    if (AlertController.AlertParams.this.mCheckedItems != null) {
                        AlertController.AlertParams.this.mCheckedItems[position] = listView.isItemChecked(position);
                    }

                    AlertController.AlertParams.this.mOnCheckboxClickListener.onClick(dialog.mDialog, position, listView.isItemChecked(position));
                }
            });
        }

        if (this.mOnItemSelectedListener != null) {
            listView.setOnItemSelectedListener(this.mOnItemSelectedListener);
        }

        if (this.mIsSingleChoice) {
            listView.setChoiceMode(1);
        } else if (this.mIsMultiChoice) {
            listView.setChoiceMode(2);
        }

        dialog.mListView = listView;
    }

    public interface OnPrepareListViewListener {
        void onPrepareListView(ListView var1);
    }
}

}
