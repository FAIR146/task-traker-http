import com.example.tasktrackerhttp.dto.Status;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Status> statuses = new ArrayList<>();
//        statuses.add(Status.NEW);
//        statuses.add(Status.NEW);
//        statuses.add(Status.NEW);
//        statuses.add(Status.DONE);
//        statuses.add(Status.IN_PROGRESS);
        System.out.println(getEpicStatus(statuses));
    }
    public static Status getEpicStatus (List<Status> list) {

        int statusNew = 0;
        int statusInProgress = 0;
        int statusDone = 0;

        for (int i = 0; i < list.size(); i++) {
            Status currentStatus = list.get(i);
            if (currentStatus == Status.IN_PROGRESS) {
                statusInProgress++;
            } else if (currentStatus == Status.NEW) {
                statusNew++;
            } else if (currentStatus == Status.DONE) {
                statusDone++;
            }
        }

        Status status = Status.UNDEFINED;
        if (statusInProgress == 0 && statusDone == 0) {
            status = Status.NEW;
        } else if (statusDone > 0){
            status = Status.IN_PROGRESS;
        } else if (statusNew == 0 && statusInProgress == 0) {
            status = Status.DONE;
        }
        return status;
    }
}
