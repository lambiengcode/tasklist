package hongvinhlcode.app.todolist.Main.Main.ToDo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import hongvinhlcode.app.todolist.Main.Main.MainActivity.MainActivity;
import hongvinhlcode.app.todolist.R;

public class ToDoAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<ToDo> toDoList;

    public ToDoAdapter(MainActivity context, int layout, List<ToDo> toDoList) {
        this.context = context;
        this.layout = layout;
        this.toDoList = toDoList;
    }

    @Override
    public int getCount() {
        return toDoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTitle,txtDes,txtRemind;
        ImageButton btnDone;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);

            holder.txtTitle=(TextView) convertView.findViewById(R.id.txtTitle);
            holder.txtDes=(TextView) convertView.findViewById(R.id.txtDes);
            holder.txtRemind=(TextView) convertView.findViewById(R.id.txtRemind);
            holder.btnDone=(ImageButton) convertView.findViewById(R.id.done);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        final ToDo toDo=toDoList.get(position);

        holder.txtTitle.setText(toDo.getTitle());
        holder.txtDes.setText(toDo.getDes());
        holder.txtRemind.setText(toDo.getRemind());

        holder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.ShowDialogDone(toDo.getId());
            }
        });

        return convertView;
    }
}

