function []=MultiRoutes()
%%-------------------------------------------------------------------------
%% ��Ҫ����˵��
%% C n���ڵ�����꣬n��2�ľ���
%% NC_max ����������
%% m ���ϴ������
%% Alpha ������Ϣ����Ҫ�̶ȵĲ���
%% Beta ��������ʽ������Ҫ�̶ȵĲ���
%% Rho ��Ϣ������ϵ��
%% Q ��Ϣ������ǿ��ϵ��
%% R_best �������·��
%% L_best �������·�ߵĳ���
%%=========================================================================
clc
clear
% ���ó�ʼ�������£�
m=30;Alpha=2;Beta=0.5;Rho=0.05;NC_max=100;Q=0.02;
%�趨ԴĿ�Ľڵ�
s=1;e=14;n=14;
% ��������Ϊ��
D=[  0        2100     3000        Inf         Inf         Inf         Inf         4800        Inf    Inf         Inf         Inf         Inf         Inf
     2100     0        1200        1500         Inf        Inf         Inf         Inf         Inf    Inf         Inf         Inf         Inf         Inf
     3000     1200     0           Inf         Inf         3600        Inf         Inf         Inf    Inf         Inf         Inf         Inf         Inf
     Inf      1500     Inf         0           1200        Inf         Inf         Inf         Inf    Inf        3900         Inf         Inf         Inf
     Inf      Inf      Inf         1200           0        2400        1200        Inf         Inf    Inf         Inf         Inf         Inf         Inf
     Inf      Inf      3600        Inf        2400         0           Inf         Inf         Inf    2100         Inf         Inf         Inf        3600
     Inf      Inf      Inf         Inf        1200         Inf           0         1500        Inf    2700         Inf         Inf         Inf         Inf
     4800     Inf      Inf         Inf         Inf         Inf         1500        0           1500   Inf         Inf         Inf         Inf         Inf
     Inf      Inf      Inf         Inf         Inf         Inf         Inf         1500        0      1500         Inf         600         600         Inf
     Inf      Inf      Inf         Inf         Inf         2100        2700        Inf         1500   0           Inf         Inf         Inf         Inf
     Inf      Inf      Inf         3900        Inf         Inf         Inf         Inf         Inf    Inf           0        1200        1500         Inf
     Inf      Inf      Inf         Inf         Inf         Inf         Inf         Inf         600    Inf        1200           0         Inf         600
     Inf      Inf      Inf         Inf         Inf         Inf         Inf         Inf         600    Inf        1500         Inf           0         300
     Inf      Inf      Inf         Inf         Inf        3600         Inf         Inf         Inf    Inf         Inf         600         300           0];
C=[ 10  80
    0   60
    10  30
    30  50 
    40  40
    50  10 
    60  50
    70  60
    100 75
    80  0
    110 130
    135 120
    140 100
    130 40];
%��ʼ������
Eta=zeros(n,n);
for k = 1:n
    for t = 1:n
        if D(k,t)~=0
           Eta(k,t)=1/D(k,t);
        else Eta(k,t)=0;
        end
    end
end        %EtaΪ�������ӣ�������Ϊ����ĵ���
Tau=ones(n,n);     %TauΪ��Ϣ�ؾ���
NC=1;               %��������������¼��������
R_best=zeros(NC_max,n);       %�������·��
R_S_best=zeros(NC_max,n); 
R_T_best=zeros(NC_max,n); 
L_best=zeros(NC_max,1);   %�������·�ߵĳ���
L_S_best=zeros(NC_max,1);
L_T_best=zeros(NC_max,1);
L_ave=zeros(NC_max,1);        %����·�ߵ�ƽ������ 
Tabu=zeros(m,n);   %�洢����¼·��������
%**************************************************************************
while NC<=NC_max        %ֹͣ����֮һ���ﵽ������������ֹͣ   
%%�ڶ�������mֻ���ϴ���ŵ�Դ�ڵ���
Tabu(:,1)=s*ones(m,1);                                                      ������������������
%%��������mֻ���ϴ������ʺ���ѡ����һ�ڵ㣬����Ŀ�Ľڵ�
hop=zeros(1,m);%���ε���������1��mѡ���·��������
dead_ants=zeros(1,m);
for i=1:m
 for j=1:(n-1)  %����i��j��Ѱ·�����n-1��
     visited=Tabu(i,1:j);  %����i�ѷ��ʹ��Ľڵ�
     nod_neighbour=find(D(visited(j),:)~=0&D(visited(j),:)~=inf);  %Ѱ�ҵ�ǰ�ڵ�visited(j)���ڵĽڵ�
     nod_avaliable=[];
     for t=1:length(nod_neighbour)
         if isempty(find(visited==nod_neighbour(t), 1))
            nod_avaliable=[nod_avaliable nod_neighbour(t)];  %�����ڽڵ���ȥ���ѷ��ʹ��Ľڵ�
         end
     end
    if length(nod_avaliable)==0
        dead_ants(i)=1;
        break;
    end
    P=zeros(1,length(nod_avaliable));       %���ʾ���
    for k=1:length(nod_avaliable)
        P(k)=(Tau(visited(j),nod_avaliable(k))^Alpha)*(Eta(visited(j),nod_avaliable(k))^Beta);
    end
    %�����㷨 ѡ����һ�ڵ�
    P=P/(sum(P)); 
    Pcum=cumsum(P); 
    Select=find(Pcum>=rand); 
%     r_0=0.2;
%     if rand>r_0
    to_visit=nod_avaliable(Select(1));
%     else
%     to_visit=nod_avaliable(find(max(P))); 
%     end
    Tabu(i,j+1)=to_visit;
    if to_visit==e  %����һ����Ŀ�Ľڵ㣬��������i��Ѱ·
        hop(i)=j;    %��������i������
        break;
    end
 end
end
% dead_ants     %���dead_ants------------------
%%%%%if NC>=2
%%%%%Tabu(1,:)=R_best(NC-1,:);
%%%%%end
%%���Ĳ�����¼���ε������·��
L=zeros(m,1);     %��ʼ����Ϊ0��m*1��������
for t=1:m
R=Tabu(t,:);
if dead_ants(t)==1
    L(t)=inf; continue;
end
for k=1:(hop(t))
L(t)=L(t)+D(R(k),R(k+1));    %ԭ������ϵ�j���ڵ㵽��j+1���ڵ�ľ���
end
end
L_best(NC)=min(L);           %��Ѿ���ȡ��С
pos=find(L==L_best(NC));
R_best(NC,:)=Tabu(pos(1),:); %���ֵ���������·��
A=sort(L);
pos1=find(L==A(length(pos)+1));
L_S_best(NC)=L(pos1(1));
R_S_best(NC,:)=Tabu(pos1(1),:);
pos2=find(L==A(length(pos)+length(pos1)+1));
L_T_best(NC)=L(pos2(1));
R_T_best(NC,:)=Tabu(pos2(1),:);

sum_L=0; p=0;          %���ֵ������ƽ������
for t=1:m
    if dead_ants(t)~=1
        sum_L=sum_L+L(t);
        p=p+1;
    end
end
L_ave(NC)=sum_L/p;    

%%���岽��������Ϣ��
Delta_Tau=zeros(n,n);        %��ʼʱ��Ϣ��Ϊn*n��0����
% for t=1:m
% for k=1:hop(t)
% Delta_Tau(Tabu(t,k),Tabu(t,k+1))=Delta_Tau(Tabu(t,k),Tabu(t,k+1))+Q*L_best(NC)/L(t);
% %�˴�ѭ����·����t��k���ϵ���Ϣ������
% end
% end
SS=zeros(n,n);%��¼�Ѿ�������Ϣ�ص���·�������ظ�����
for k=1:hop(pos(1))
   Delta_Tau(Tabu(pos(1),k),Tabu(pos(1),k+1))=Delta_Tau(Tabu(pos(1),k),Tabu(pos(1),k+1))+Q;
   SS(Tabu(pos(1),k),Tabu(pos(1),k+1))=1;
end  

for k=1:hop(pos1(1))
    if SS(Tabu(pos1(1),k),Tabu(pos1(1),k+1))==0
   Delta_Tau(Tabu(pos1(1),k),Tabu(pos1(1),k+1))=Delta_Tau(Tabu(pos1(1),k),Tabu(pos1(1),k+1))+Q;
   SS(Tabu(pos1(1),k),Tabu(pos1(1),k+1))=1;
    end
end
for k=1:hop(pos2(1))
    if SS(Tabu(pos2(1),k),Tabu(pos2(1),k+1))==0
   Delta_Tau(Tabu(pos2(1),k),Tabu(pos2(1),k+1))=Delta_Tau(Tabu(pos2(1),k),Tabu(pos2(1),k+1))+Q;
   SS(Tabu(pos2(1),k),Tabu(pos2(1),k+1))=1;
    end
end

Tau=(1-Rho).*Tau+Delta_Tau; %������Ϣ�ػӷ������º����Ϣ��
%%�����������ɱ�����
Tabu=zeros(m,n);             %%ֱ������������
NC=NC+1;                    %��������
end
%**************************************************************************
%%���߲���������

Shortest_Route=R_best(NC_max,:); %���������������·��
Shortest_Length=L_best(NC_max) %��������������̾���
Opti_Route=Shortest_Route(1:length(find(Shortest_Route~=0)))
R_best;
Shortest_S_Route=R_S_best(NC_max,:);
Shortest_S_Length=L_S_best(NC_max) 
Opti_Route_S=Shortest_S_Route(1:length(find(Shortest_S_Route~=0)))
Shortest_T_Route=R_T_best(NC_max,:);
Shortest_T_Length=L_T_best(NC_max)
Opti_Route_T=Shortest_T_Route(1:length(find(Shortest_T_Route~=0)))
drafig(C,D,Opti_Route,L_best,n,1);
drafig(C,D,Opti_Route_S,L_S_best,n,2);
drafig(C,D,Opti_Route_T,L_T_best,n,3);
figure(4);                           
plot(L_best,'g');
hold on ;         %����ͼ��
plot(L_S_best,'y');
hold on ;  
plot(L_T_best,'b');
hold on ;  
plot(L_ave,'r');
title('ƽ���������̾���');%����
% Tau;

function []=drafig(C,D,Opti_Route,L_best,n,ww)
figure(ww);
scatter(C(:,1),C(:,2),'.');
hold on;
for t = 1:n-1
    for m = (t+1):n
        if D(t,m)~=0&&D(t,m)~=inf
        plot([C(t,1);C(m,1)],[C(t,2);C(m,2)],'b');hold on;
        end
    end
end
for i=1:n
text(C(i,1)+3,C(i,2)+3,int2str(i));
end
DrawRoute(C,Opti_Route);  hold on;

function DrawRoute(C,R)
%%=========================================================================
%% DrawRoute.m
%% ��·��ͼ���Ӻ���
%%-------------------------------------------------------------------------
%% C Coordinate �ڵ����꣬��һ��N��2�ľ���洢
%% R Route ·��
%%=========================================================================
N=length(R);
scatter(C(:,1),C(:,2),'o');
hold on
for ii=2:N
plot([C(R(ii-1),1);C(R(ii),1)],[C(R(ii-1),2);C(R(ii),2)],'r','LineWidth',2);
hold on
end
title('����·�� ')


