# Spring-Reddit-Clone
1. Máy khách gửi 1 yêu cầu đăng nhập đén máy chủ
2. Máy chủ kiểm tra thông tin đăng nhập do người dùng cung cấp, nếu thông tin đăng nhập là đúng, nó sẽ tạo ra 
Mã thông báo web JSON (JWT). 
3. Nó phản hồi bằng một thông báo thành công và JWT
4. Máy khách sử dụng JWT này trong tất cả cấc yêu cầu tiếp theo cho người dùng, nó cung cấp JWT này như một 
tiêu đề ủy quyền với lược đồ xác thực Bearer
5. Khi máy chủ nhận đươc yêu cầu đối với một điểm cuối được bảo mật, nó sẽ kiểm tra JWT và xác thực 
xem mã thông báo có được tạo và ký bởi máy chủ hay không
6. Xác nhận thành công, máy chủ phản hồi tương ứng với máy khách

A. Spring Security Authentication Flow
https://i1.wp.com/programmingtechie.com/wp-content/uploads/2019/11/Spring-Login-Flow.png?resize=768%2C432&ssl=1
+) Yêu cầu đăng nhập được nhận bởi *AuthenticationController* và được chuyển cho lớp AuthService.

+) Lớp này tạo một đối tượng kiểu *UserNamePasswordAutheticationToken* đóng gói tên người dùng và
mật khẩu do người dùng cung cấ như một thành phần của yêu cầu đăng nhập.

+) Sau đó ddiefu này được chuyển cho *AuthenticationManager* đảm nhiệm phần xác thực khi sử dụng Sping Security.

+) Các *AuthenticationManager* tiếp tục tương tác với một giao diện được gọi là UserDetailsServic,
giao diện này cho thấy giao dịch với dữ liệu người dùng.

+) Khi lưu trữ thông tin người dùng của mình bên trong Cơ sở dữ liệu, sử dụng xác thực Cơ sở dữ liệu,
vì vậy việc triển khai truy cập cơ sở dữ liệu và truy xuất  chi tiết người dùng và 
chuyển  *UserDetails*  trở lại  *AuthenticationManager* .

+) Các *AuthenticationManger* hiện kiểm tra các thông tin và nếu chúng phù hợp nó tạo ra một đối tượng 
*Authentication* và chuyển nó trở lại lớp  *AuthService*

+) Sau đó, tạo JWT và phản hồi lại ngưởi dùng 

B.  Định cấu hình SeecurityConfig với AuthenticationManager
+) Thay đổi lớp SecurityConfig và thêm cấu hình cần thiết cho *AuthenticationManager*
