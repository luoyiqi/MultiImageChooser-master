package com.demo.mummyding.multiimagechooser.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.demo.mummyding.multiimagechooser.R;
import com.demo.mummyding.multiimagechooser.model.ImageBean;
import com.demo.mummyding.multiimagechooser.ui.ImageDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择界面的list适配器
 * Created by mummyding on 15-11-3.
 */
public class SelectImageAdapter extends BasicAdapter implements AdapterView.OnItemClickListener{
    public List<ImageBean> checkedList = new ArrayList<>(); // 已经选中的图片列表
    public SelectImageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        final ImageBean imageBean = (ImageBean) getItem(position);
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_choose_image,null);
            viewHolder.image = (SimpleDraweeView) convertView.findViewById(R.id.image);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.checkBox.setChecked(imageBean.isChecked());
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                imageBean.setIsChecked(isChecked);
                if (checkedList.contains(imageBean)) {
                    if (imageBean.isChecked() == false) {
                        checkedList.remove(imageBean);
                    }
                } else if (imageBean.isChecked()) {
                    checkedList.add(imageBean);
                }
                ((AppCompatActivity) mContext).getSupportActionBar().setTitle(mContext.getString(R.string.text_selected_img)+"(" + checkedList.size() + ")");
            }
        });
        ((AppCompatActivity) mContext).getSupportActionBar().setTitle(mContext.getString(R.string.text_selected_img)+"(" + checkedList.size() + ")");

        viewHolder.checkBox.setChecked(imageBean.isChecked());
        viewHolder.image.setLayoutParams(frameParams);
        viewHolder.image.setImageURI(Uri.parse(imageBean.getImageUri()));
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, ImageDetailsActivity.class);
        intent.putExtra(mContext.getString(R.string.id_image_uri),((ImageBean)getItem(position)).getImageUri().toString());
        mContext.startActivity(intent);
    }

    class ViewHolder{
        SimpleDraweeView image;
        CheckBox checkBox;
    }
}
