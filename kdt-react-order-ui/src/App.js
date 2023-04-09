import logo from './logo.svg';
import './App.css';

function App() {
  return (
 <div className="container-fluid">
 <div className="row justify-content-center m-4">
   <h1 className="text-center">Grids & Circle</h1>
 </div>
 <div className="card">
   <div className="row">
     <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
       <h5 className="flex-grow-0"><b>상품 목록</b></h5>
       <ul className="list-group products">
         <li className="list-group-item d-flex mt-3">
           <div className="col-2"><img class="img-fluid" src="https://i.imgur.com/HKOFQYa.jpeg" alt=""/></div>
           <div className="col">
             <div className="row text-muted">커피콩</div>
             <div className="row">Columbia Nariñó</div>
           </div>
           <div className="col text-center price">5000원</div>
           <div className="col text-end action"><a class="btn btn-small btn-outline-dark" href="">추가</a></div>
         </li>
         <li className="list-group-item d-flex mt-2">
           <div className="col-2"><img class="img-fluid" src="https://i.imgur.com/HKOFQYa.jpeg" alt=""/></div>
           <div className="col">
             <div className="row text-muted">커피콩</div>
             <div className="row">Columbia Nariñó</div>
           </div>
           <div className="col text-center price">5000원</div>
           <div className="col text-end action"><a class="btn btn-small btn-outline-dark" href="">추가</a></div>
         </li>
         <li className="list-group-item d-flex mt-2">
           <div className="col-2"><img class="img-fluid" src="https://i.imgur.com/HKOFQYa.jpeg" alt=""/></div>
           <div className="col">
             <div className="row text-muted">커피콩</div>
             <div className="row">Columbia Nariñó</div>
           </div>
           <div className="col text-center price">5000원</div>
           <div className="col text-end action"><a class="btn btn-small btn-outline-dark" href="">추가</a></div>
         </li>
       </ul>
     </div>
     <div className="col-md-4 summary p-4">
       <div>
         <h5 className="m-0 p-0"><b>Summary</b></h5>
       </div>
       <hr/>
       <div className="row">
         <h6 className="p-0">Columbia Nariñó <span class="badge bg-dark text-">2개</span></h6>
       </div>
       <div className="row">
         <h6 className="p-0">Brazil Serra Do Caparaó <span class="badge bg-dark">2개</span></h6>
       </div>
       <div className="row">
         <h6 className="p-0">Columbia Nariñó <span class="badge bg-dark">2개</span></h6>
       </div>
       <form>
         <div className="mb-3">
           <label for="email" class="form-label">이메일</label>
           <input type="email" class="form-control mb-1" id="email"/>
         </div>
         <div className="mb-3">
           <label for="address" class="form-label">주소</label>
           <input type="text" class="form-control mb-1" id="address"/>
         </div>
         <div className="mb-3">
           <label for="postcode" class="form-label">우편번호</label>
           <input type="text" class="form-control" id="postcode"/>
         </div>
         <div>당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</div>
       </form>
       <div className="row pt-2 pb-2 border-top">
         <h5 className="col">총금액</h5>
         <h5 className="col text-end">15000원</h5>
       </div>
       <button className="btn btn-dark col-12">결제하기</button>
     </div>
   </div>
 </div>
 </div>
  );
}

export default App;
