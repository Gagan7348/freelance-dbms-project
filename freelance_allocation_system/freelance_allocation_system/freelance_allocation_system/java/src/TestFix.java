import dao.ProjectDAO;
import model.Project;
import java.util.List;

public class TestFix {
    public static void main(String[] args) {
        ProjectDAO dao = new ProjectDAO();
        List<Project> pending = dao.getProjectsByStatus("PENDING");
        System.out.println("Pending projects count: " + pending.size());
        for(Project p : pending) {
            System.out.println("Project: " + p.getTitle() + " | Deadline: " + p.getDeadline());
        }
    }
}
