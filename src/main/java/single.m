function []=costs()
%%-------------------------------------------------------------------------
%% 主要符号说明
%% C n个节点的坐标，n×2的矩阵
%% NC_max 最大迭代次数
%% m 蚂蚁代理个数
%% Alpha 表征信息素重要程度的参数
%% Beta 表征启发式因子重要程度的参数
%% Rho 信息素蒸发系数
%% Q 信息素增加强度系数
%% R_best 各代最佳路线
%% L_best 各代最佳路线的长度
%%=========================================================================
clc
clear
% 设置初始参数如下：
m=10;Alpha=2;Beta=0.5;Rho=0.1;NC_max=100;Q=0.02;
%设定源目的节点
s=1;e=14;n=14;
% 31节点坐标为：
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
Eta=zeros(n,n);
for k = 1:n
    for t = 1:n
        if D(k,t)~=0
           Eta(k,t)=1/D(k,t);
        else Eta(k,t)=0;
        end
    end
end        %Eta为启发因子，这里设为距离的倒数
Tau=ones(n,n);     %Tau为信息素矩阵
NC=1;               %迭代计数器，记录迭代次数
R_best=zeros(NC_max,n);       %各代最佳路线
L_best=zeros(NC_max,1);   %各代最佳路线的长度
L_ave=zeros(NC_max,1);        %各代路线的平均长度
Tabu=zeros(m,n);   %存储并记录路径的生成
%**************************************************************************
while NC<=NC_max        %停止条件之一：达到最大迭代次数，停止
%%第二步：将m只蚂蚁代理放到源节点上
Tabu(:,1)=s*ones(m,1);   
%%第三步：m只蚂蚁代理按概率函数选择下一节点，到达目的节点
hop=zeros(1,m);%本次迭代中蚂蚁1：m选择的路径的跳数
dead_ants=zeros(1,m);
for i=1:m
 for j=1:(n-1)  %蚂蚁i第j次寻路，最多n-1次
     visited=Tabu(i,1:j);  %蚂蚁i已访问过的节点
     nod_neighbour=find(D(visited(j),:)~=0&D(visited(j),:)~=inf);  %寻找当前节点visited(j)相邻的节点
     nod_avaliable=[];
     for t=1:length(nod_neighbour)
         if isempty(find(visited==nod_neighbour(t), 1))
            nod_avaliable=[nod_avaliable nod_neighbour(t)];  %从相邻节点中去除已访问过的节点
         end
     end
    if length(nod_avaliable)==0
        dead_ants(i)=1;
        break;
    end
    P=zeros(1,length(nod_avaliable));       %概率矩阵
    for k=1:length(nod_avaliable)
        P(k)=(Tau(visited(j),nod_avaliable(k))^Alpha)*(Eta(visited(j),nod_avaliable(k))^Beta);
    end
    %轮盘算法 选择下一节点
    P=P/(sum(P)); 
    Pcum=cumsum(P); 
    Select=find(Pcum>=rand); 
    to_visit=nod_avaliable(Select(1));
    Tabu(i,j+1)=to_visit;
    if to_visit==e  %若下一跳是目的节点，结束蚂蚁i的寻路
        hop(i)=j;    %记下蚂蚁i的跳数
        break;
    end
 end
end
% dead_ants;     %输出dead_ants------------------
%%%%%if NC>=2
%%%%%Tabu(1,:)=R_best(NC-1,:);
%%%%%end
%%第四步：记录本次迭代最佳路线
L=zeros(m,1);     %开始距离为0，m*1的列向量
for t=1:m
R=Tabu(t,:);
if dead_ants(t)==1
    L(t)=inf;continue;
end
for k=1:(hop(t))
L(t)=L(t)+D(R(k),R(k+1));    %原距离加上第j个节点到第j+1个节点的距离
end
end
L_best(NC)=min(L);           %最佳距离取最小
pos=find(L==L_best(NC));
R_best(NC,:)=Tabu(pos(1),:); %此轮迭代后的最佳路线
sum_L=0; p=0;          %此轮迭代后的平均距离
for t=1:m
    if dead_ants(t)~=1
        sum_L=sum_L+L(t);
        p=p+1;
    end
end
L_ave(NC)=sum_L/p;    
NC=NC+1;                    %迭代继续
%%第五步：更新信息素
Delta_Tau=zeros(n,n);        %开始时信息素为n*n的0矩阵
% for t=1:m
% for k=1:hop(t)
% Delta_Tau(Tabu(t,k),Tabu(t,k+1))=Delta_Tau(Tabu(t,k),Tabu(t,k+1))+Q/L(t);
% %此次循环在路径（t，k）上的信息素增量
% end
% end

for k=1:hop(pos(1))
   Delta_Tau(Tabu(pos(1),k),Tabu(pos(1),k+1))=Q;
end  

Tau=(1-Rho).*Tau+Delta_Tau; %考虑信息素挥发，更新后的信息素
%%第六步：禁忌表清零
Tabu=zeros(m,n);             %%直到最大迭代次数
end
%**************************************************************************
%%第七步：输出结果
Shortest_Route=R_best(NC_max,:); %最大迭代次数后最佳路径
Shortest_Length=L_best(NC_max) %最大迭代次数后最短距离
R_best;  %输出各代最佳路径
L_best;  %输出各代最佳路径的长度
Opti_Route=Shortest_Route(1:length(find(Shortest_Route~=0)))
figure(1);
scatter(C(:,1),C(:,2),'o');
hold on;
for t = 1:n-1
    for m = (t+1):31
        if D(t,m)~=0&&D(t,m)~=inf
        plot([C(t,1);C(m,1)],[C(t,2);C(m,2)],'b');hold on;
        end
    end
end
DrawRoute(C,Opti_Route);  hold on;
figure(2);                           
plot(L_best);
hold on ;         %保持图形
plot(L_ave,'r');
title('平均距离和最短距离');%标题
function DrawRoute(C,R)
%%=========================================================================
%% DrawRoute.m
%% 画路线图的子函数
%%-------------------------------------------------------------------------
%% C Coordinate 节点坐标，由一个N×2的矩阵存储
%% R Route 路线
%%=========================================================================
N=length(R);
scatter(C(:,1),C(:,2),'o');
hold on
for ii=2:N
plot([C(R(ii-1),1);C(R(ii),1)],[C(R(ii-1),2);C(R(ii),2)],'r','LineWidth',2);
hold on
end
title('最优路径 ')


