package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity (name = "EventModel")
@Table(name = "EventModel")
public class EventModel implements Serializable {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@javax.persistence.Id
		@GeneratedValue
		private int Id;
		
		@Column(name = "priority")
		private int priority;
		
		@Column(name= "date")
		private long date;
		
		@Column(name = "data")
		private String data;

		
		public EventModel() {
			super();
			// TODO Auto-generated constructor stub
		}

		public EventModel(int id, String type, long code, int priority, long date,
				String data) {
			super();
			Id = id;
			this.type = type;
			this.code = code;
			this.priority = priority;
			this.date = date;
			this.data = data;
		}

		public int getId() {
			return Id;
		}

		public void setId(int id) {
			Id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public long getCode() {
			return code;
		}

		public void setCode(long code) {
			this.code = code;
		}

		public int getPriority() {
			return priority;
		}

		public void setPriority(int priority) {
			this.priority = priority;
		}

		public long getDate() {
			return date;
		}

		public void setDate(long date) {
			this.date = date;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		
	}

}
