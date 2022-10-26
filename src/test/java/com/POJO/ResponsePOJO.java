package com.POJO;

	import java.util.List;

	public class ResponsePOJO {
		
		private String status;
		private List<DataPOJO> data;
		private String message;
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public List<DataPOJO> getData() {
			return data;
		}
		public void setData(List<DataPOJO> data) {
			this.data = data;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		@Override
		public String toString() {
			return "ResponsePOJO [status=" + this.status + ", data=" + data + ", message=" + message + "]";
		}
		

	}


