package mainClasses;

public class Booking{
    int booking_id , customer_id , event_id , ticket_id , cost , quantity;
    String date;

    public int getId() {
        return booking_id;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getEventId() {
        return event_id;
    }

    public void setEventId(int event_id) {
        this.event_id = event_id;
    }

    public int getTicketId() {
        return ticket_id;
    }

    public void setTicketId(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTicketQuantity() {
        return quantity;
    }

    public void setTicketQuantity(int quantity) {
        this.quantity = quantity;
    }
}