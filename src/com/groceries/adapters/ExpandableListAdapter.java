package com.groceries.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.groceries.R;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter{

    private LayoutInflater inflater;
    private ArrayList<String> parents;
    private ArrayList<ArrayList<String>> children;

    public ArrayList<String> getParents() 
    {
        return parents;
    }
    
    public ExpandableListAdapter(Context context,
         ArrayList<String> parentList, ArrayList<ArrayList<String>> childrenList ) 
    {
        this.parents = parentList;
        this.children = childrenList;
        this.inflater = LayoutInflater.from(context);
    }

    // counts the number of group/parent items so the list knows how many
    // times calls getGroupView() method
    public int getGroupCount() 
    {
        return parents.size();
    }

    // counts the number of children items so the list knows how many times
    // calls getChildView() method
	public int getChildrenCount(int parentPosition) 
    {
        int size =0;
        if(parents.get(parentPosition) != null && children.size() > parentPosition)
        {
        	size = children.get(parentPosition).size();
        }
        return size;
    }

    // gets the title of each parent/group
    public Object getGroup(int i) 
    {
        return parents.get(i);
    }

    // gets the name of each item
	public Object getChild(int parentPosition, int childPosition) 
    {
        return children.get(parentPosition).get(childPosition);
    }

    public long getGroupId(int parentPosition) 
    {
        return parentPosition;
    }

    public long getChildId(int i, int childPosition) 
    {
        return childPosition;
    }
    
    public int getParentByChildText(String text)
    {
    	for(int i = 0; i < getGroupCount(); ++i)
    	{
    		for(int j = 0; j < children.get(i).size(); ++j)
        	{
    			if(((String)getChild(i, j)).equals(text))
        		{
        			return i;
        		}
        	}   		
    	}
    	return -1;
    }
    
    public int getParentByText(String text)
    {
    	for(int i = 0; i < getGroupCount(); ++i)
    	{
    		if(((String)getGroup(i)).equals(text))
        	{
        		return i;
        	}	
    	}
    	return -1;
    }
    
    public int GetChildIndexByText(String text)
    {
    	for(int i = 0; i < children.size(); ++i)
    	{
    		for(int j = 0; j < children.get(i).size(); ++j)
        	{
    			if(children.get(i).get(j).equals(text))
    			{
    				return j;
    			}
        	}
    	}   	
    	return -1;
    }

    public boolean hasStableIds() 
    {
        return true;
    }

    // in this method you must set the text to see the parent/group on the list
    public View getGroupView(int parentPosition, boolean b, View view, ViewGroup viewGroup) 
    {
    	View v = null;
        if( view != null )
        {
            v = view;    
        }
        else
            v = inflater.inflate(R.layout.category_list_widget, viewGroup, false); 
        TextView parent = (TextView)v.findViewById(R.id.categoryParent);
        if( parent != null )
        {
        	parent.setText((String)getGroup(parentPosition));
        }
        return v;
    }

    // in this method you must set the text to see the children on the list
    public View getChildView(int parentPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) 
    {
    	View v = null;
        if( view != null )
            v = view;
        else
            v = inflater.inflate(R.layout.grocery_list_widget, null, true); 
        TextView child = (TextView)v.findViewById(R.id.categoryChild);
        if( child != null )
        {
        	child.setText(((String)getChild(parentPosition, childPosition)));
        }
       
        return v;
    }

    public boolean isChildSelectable(int i, int i1) 
    {
        return true;
    }
    
    public void removeChild(int groupPosition, int childPosition) 
    {
        if (getChildrenCount(groupPosition)>0 && getChildrenCount(groupPosition)-1 >= childPosition )
        {
        	children.get(groupPosition).remove(childPosition);
        	notifyDataSetChanged();
        }
    }
    
    public void AddChild(int groupPosition, String child) 
    {
        
    	children.get(groupPosition).add(child);
        notifyDataSetChanged();
    }
    
    public void AddGroup(String name)
    {
    	parents.add(name);
    	children.add(new ArrayList<String>());
    	notifyDataSetChanged();
    }
    
    public void RemoveGroup(String name)
    {
    	int index = parents.indexOf(name);
    	
    	parents.remove(index);
    	if(children.size() > index)
    	{
    		children.remove(index);
    	}
    	
    	notifyDataSetChanged();
    }
    
}
